package wmi.amu.edu.pl.pri.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.JSONChecklistObj;
import wmi.amu.edu.pl.pri.domainobject.ChecklistTally;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.models.ChapterVersionModel;
import wmi.amu.edu.pl.pri.models.ChecklistModel;
import wmi.amu.edu.pl.pri.models.ChecklistQuestionModel;
import wmi.amu.edu.pl.pri.models.ThesisModel;
import wmi.amu.edu.pl.pri.repositories.ChecklistRepo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChecklistService {
    private final ChecklistRepo repo;
    private final VersionService versionService;
    private final ChecklistQuestionService questionService;

    public ChecklistDto getChecklistByVersionId(Long id) {
        Optional<ChecklistModel> optional = repo.findByVersionId(id);
        if (optional.isEmpty()) {
            try {
                ChecklistModel model = generateChecklistFromJson(id);
                return model.toChecklistDto();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return optional.get().toChecklistDto();
    }

    public void setChecklist(ChecklistDto dto) {
        Optional<ChecklistModel> optional = findChecklistByVersionId(dto.getVersionId());
        if (optional.isPresent()) {
            ChecklistModel model = optional.get();
            model.setDate(new Date());
            model.setPassed(dto.isPassed());
            model.setChecklistQuestionModels(dto.getModels());
            for (ChecklistQuestionModel question : model.getChecklistQuestionModels()) {
                questionService.saveQuestion(question);
            }
            repo.save(model);
        }
    }

    private Optional<ChecklistModel> findChecklistByVersionId(Long id) {
        return repo.findByVersionId(id);

    }

    private boolean checkIfPassed(List<ChecklistQuestionModel> list) {
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
                question.setPoints(0);
                list.add(question);
                questionService.saveQuestion(question);
            }
            model.setPassed(false);
            model.setChecklistQuestionModels(list);
            ChapterVersionModel version = versionService.getChapterVersionById(id);
            if (version == null) {
                throw new IllegalArgumentException("ChapterVersion not found for id: " + id);
            }
            model.setVersionModel(version);
            model.setDate(new Date());
            repo.save(model);

            return model;
        }

    }

    public ChecklistTally getChecklistTallyByVersion(ChapterVersionModel chapterVersion /* albo id, jak wolisz */) {
        //todo
        return new ChecklistTally();
    }

    public List<ChecklistTally> getChecklistTalliesByThesis(ThesisModel thesisModel /* albo id, jak wolisz*/ ){
        //todo najwygodniej by bylo, jakbys to zrobil
        return Collections.emptyList();
    }
}
