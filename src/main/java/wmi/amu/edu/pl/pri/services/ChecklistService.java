package wmi.amu.edu.pl.pri.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.JSONChecklistObj;
import wmi.amu.edu.pl.pri.dto.ChapterVersionsDto;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.models.ChecklistModel;
import wmi.amu.edu.pl.pri.models.ChecklistQuestionModel;
import wmi.amu.edu.pl.pri.repositories.ChecklistRepo;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChecklistService {
    private final ChecklistRepo repo;
    private final StudentService studentService;

    public ChecklistDto getChecklistByStudentId(Integer id){
        Optional<ChecklistModel> optional = repo.findByStudentId(id);
        if(optional.isEmpty()){
            try {
                ChecklistModel model = generateChecklistFromJson(id);
                return mapToChecklistDto(model);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return mapToChecklistDto(optional.get());
    }

    public ChecklistDto mapToChecklistDto(ChecklistModel model){
        return ChecklistDto.builder()
                .studentId(model.getId())
                .uploadTime(model.getDate())
                .models(model.getChecklistQuestionModels())
                .isPassed(model.isPassed())
                .build();
    }
    public void setChapterlist(ChecklistDto dto){
        this.setChecklistduo(dto.getModels(),dto.getStudentId());
    }

    private void setChecklistduo(List<ChecklistQuestionModel> models, Integer id){
       ChecklistModel model = getChecklistBysId(id);
       model.setDate(new Date());
       model.setChecklistQuestionModels(models);
       model.setStudent(studentService.getStudentById(id));
       model.setPassed(checkIfPassed(model.getChecklistQuestionModels()));
       repo.save(model);
    }

    private ChecklistModel getChecklistBysId(Integer id){
        Optional<ChecklistModel> optional = repo.findByStudentId(id);
        return optional.get();
    }

    private boolean checkIfPassed(List<ChecklistQuestionModel> list){
        return list.stream()
                .noneMatch(q -> q.isCritical() && q.getPoints() == 0);

    }
    private ChecklistModel generateChecklistFromJson(int id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("questions.json");
        List<JSONChecklistObj> questions = mapper.readValue(
                jsonFile,
                mapper.getTypeFactory().constructCollectionType(List.class, JSONChecklistObj.class)
        );
        ChecklistModel model = new ChecklistModel();

        List<ChecklistQuestionModel> list = new ArrayList<>();
        for(JSONChecklistObj o : questions){
            ChecklistQuestionModel question = new ChecklistQuestionModel();
            question.setQuestion(o.getQuestion());
            question.setCritical(o.isCritical());
            question.setPoints(-1);
            list.add(question);
        }

        model.setPassed(false);
        model.setChecklistQuestionModels(list);
        model.setStudent(studentService.getStudentById(id));
        model.setDate(new Date());

        return model;
    }


}
