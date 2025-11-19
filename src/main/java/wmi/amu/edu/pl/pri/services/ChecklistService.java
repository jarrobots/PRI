package wmi.amu.edu.pl.pri.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.JSONChecklistObj;
import wmi.amu.edu.pl.pri.domainobject.ChecklistTally;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.models.*;
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
    private final UserChecklistTemplateService templateService;
    private final ChapterService chapterService;
    private final UserDataService userDataService;

    public ChecklistDto getChecklistByVersionId(Long id, Long userId) {
        Optional<ChecklistModel> optional = repo.findByVersionId(id);
        if (optional.isEmpty()) {
            var model = generateChecklistFromDB(userId);
            return model.toChecklistDto();
        }
        return optional.get().toChecklistDto();
    }

    public ChecklistDto getChecklistByChapterId(Long id) {
        Optional<ChecklistModel> optional = repo.findByChapterId(id);
        if (optional.isEmpty()) {
            Long ownerId = chapterService.getById(id).getOwner().getId();
            var model = generateChecklistFromDB(ownerId);
            return model.toChecklistDto();

        }
        return optional.get().toChecklistDto();
    }

    public void setChecklist(ChecklistDto dto) {
        Optional<ChecklistModel> optional = findChecklistByVersionId(dto.getVersionId());
        if (optional.isPresent()) {
            ChecklistModel model = optional.get();
            model.setDate(new Date());
            model.setChecklistQuestionModels(dto.getModels());
            for (ChecklistQuestionModel qModel : model.getChecklistQuestionModels()) {
                ChecklistQuestionModel question = questionService.getQuestion(qModel);
                question.setPassed(qModel.isPassed());
                questionService.saveQuestion(question);
            }
            repo.save(model);
        }
    }

    private Optional<ChecklistModel> findChecklistByVersionId(Long id) {
        return repo.findByVersionId(id);

    }

    private ChecklistModel generateChecklistFromDB(Long userID){
        var list = templateService.getChecklistTemplates(userID);
        var questions = new ArrayList<ChecklistQuestionModel>();
        for(String item : list){
            ChecklistQuestionModel question = new ChecklistQuestionModel();
            question.setQuestion(item);
            question.setPassed(false);
            questionService.saveQuestion(question);
            questions.add(question);
        }
        var model  = new ChecklistModel();
        model.setChecklistQuestionModels(questions);

        Long id = chapterService.findChapterByOwnerId(userID).getId();
        model.setVersionModel(versionService.getChapterVersionById(id));
        model.setDate(new Date());
        repo.save(model);

        return model;
    }

    private int getPassed(List<ChecklistQuestionModel> models){
        int summary = 0;
        for( ChecklistQuestionModel model : models){
            if(model.isPassed()) summary++;
        }
        return summary;
    }

    public Optional<ChecklistTally> getChecklistTallyByVersion(ChapterVersionModel versionModel) {
        return repo.findByVersionId(versionModel.getId()).map(
                checklistModel-> new ChecklistTally(checklistModel.getVersionModel().getId(),
                        checklistModel.getChecklistQuestionModels().size(),
                        getPassed(checklistModel.getChecklistQuestionModels())));
    }

    public List<ChecklistTally> getCheckListByChapter(ChapterModel chapterModel){
        List<ChecklistTally> list = new ArrayList<>();
        for(ChapterVersionModel model : chapterModel.getVersions()){
            getChecklistTallyByVersion(model).ifPresent(list::add);
        }
        return list;
    }

    public List<ChecklistTally> getChecklistTalliesByThesis(ThesisModel thesisModel) {
        List<ChecklistTally> list = new ArrayList<>();
        for(ChapterModel model : thesisModel.getChapters()){
            list.addAll(getCheckListByChapter(model));
        }

        return list;
    }
/*
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
                System.out.println(o.getQuestion());
                question.setQuestion(o.getQuestion());
                question.setPassed(false);
                list.add(question);
                questionService.saveQuestion(question);
            }
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
 */
}
