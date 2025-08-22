package wmi.amu.edu.pl.pri.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.JSONChecklistObj;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.models.ChecklistModel;
import wmi.amu.edu.pl.pri.models.ChecklistQuestionModel;
import wmi.amu.edu.pl.pri.repositories.ChecklistRepo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChecklistService {
    private final ChecklistRepo repo;
    private final VersionService versionService;

    public ChecklistDto getChecklistByVersionId(Long id){
        Optional<ChecklistModel> optional = repo.findByVersionId(id);
        if(optional.isEmpty()){
            try {
                ChecklistModel model = generateChecklistFromJson(id);
                return model.toChecklistDto();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return optional.get().toChecklistDto();
    }
    public void setChapterlist(ChecklistDto dto){
        this.setChecklistdto(dto.getModels(),dto.getVersionId());
    }

    private void setChecklistdto(List<ChecklistQuestionModel> models, Long id){
       ChecklistModel model = getChecklistBysId(id);
       model.setDate(new Date());
       model.setChecklistQuestionModels(models);
       repo.save(model);
    }

    private ChecklistModel getChecklistBysId(Long id){
        Optional<ChecklistModel> optional = repo.findByVersionId(id);
        return optional.get();
    }

    private boolean checkIfPassed(List<ChecklistQuestionModel> list){
        return list.stream()
                .noneMatch(q -> q.isCritical() && q.getPoints() == 0);

    }
    private ChecklistModel generateChecklistFromJson(Long id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("questions.json")) {
            if (is == null) {
                throw new FileNotFoundException("questions.json not found in resources!");
            }
            List<JSONChecklistObj> questions = mapper.readValue(
                    is,
                    mapper.getTypeFactory().constructCollectionType(List.class, JSONChecklistObj.class)
            );

            ChecklistModel model = new ChecklistModel();
            List<ChecklistQuestionModel> list = new ArrayList<>();
            for (JSONChecklistObj o : questions) {
                ChecklistQuestionModel question = new ChecklistQuestionModel();
                question.setQuestion(o.getQuestion());
                question.setCritical(o.isCritical());
                question.setPoints(-1);
                list.add(question);
            }

            model.setPassed(false);
            model.setChecklistQuestionModels(list);
            model.setVersionModel(versionService.getChapterVersionById(id));
            model.setDate(new Date());

            return model;
        }

    }
}
