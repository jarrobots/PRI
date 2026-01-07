package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.domainobject.ChecklistTally;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.models.*;
import wmi.amu.edu.pl.pri.repositories.ChecklistRepo;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChecklistService {
    private final ChecklistRepo repo;
    private final VersionService versionService;
    private final ChecklistQuestionService questionService;
    private final ThesisChecklistTemplateService thesisTemplateService;
    private final ChapterChecklistTemplateService chapterTemplateService;
    private final ChapterService chapterService;
    private final ThesisService thesisService;

    public ChecklistDto getChecklistByVersionId(Long id) {
        Optional<ChecklistModel> optional = repo.findByVersionId(id);
        if (optional.isEmpty()) {
            var list = chapterTemplateService.getChecklistTemplates();
            var model = generateVersionChecklistFromDB(list, id);
            return model.toChecklistDto();
        }
        return optional.get().toChecklistDto();
    }


    public ChecklistDto getChecklistByThesisId(Long thesisId) {
        Optional<ChecklistModel> optional = repo.findByThesisId(thesisId);
        if (optional.isEmpty()) {
            var list = thesisTemplateService.getChecklistTemplates();
            var model = generateThesisChecklistFromDB(list, thesisId);
            return model.toChecklistDto();

        }
        return optional.get().toChecklistDto();
    }

    public void setChecklist(ChecklistDto dto) {
        ChecklistModel model;
        if (repo.findByThesisId(dto.getThesisId()).isPresent()) {
            model = repo.findByThesisId(dto.getThesisId()).orElse(null);
            saveChcecklist(model, dto);
        }
        else if(repo.findByVersionId(dto.getVersionId()).isPresent()) {
            model = repo.findByVersionId(dto.getVersionId()).orElse(null);
            saveChcecklist(model, dto);
        }
        else throw new RuntimeException("Checklist not defined properly");

    }

    private void saveChcecklist(ChecklistModel model, ChecklistDto dto) {
        model.setDate(new Date());
        model.setChecklistQuestionModels(dto.getModels());
        for (ChecklistQuestionModel qModel : model.getChecklistQuestionModels()) {
            ChecklistQuestionModel question = questionService.getQuestion(qModel);
            question.setPassed(qModel.isPassed());
            questionService.saveQuestion(question);
        }
        repo.save(model);
    }

    private ChecklistModel generateVersionChecklistFromDB(List<String> list, Long versionId) {
        var questions = generateQuestions(list);
        var model  = new ChecklistModel();
        model.setChecklistQuestionModels(questions);
        model.setVersionModel(versionService.getChapterVersionById(versionId));
        model.setDate(new Date());
        repo.save(model);

        return model;
    }

    private ChecklistModel generateThesisChecklistFromDB(List<String> list, Long thesisId) {
        var thesisModel = thesisService.findById(thesisId).get();
        var questions = generateQuestions(list);
        var model  = new ChecklistModel();
        model.setChecklistQuestionModels(questions);
        model.setThesisModel(thesisModel);
        model.setDate(new Date());
        repo.save(model);

        return model;
    }

    private List<ChecklistQuestionModel> generateQuestions(List<String> list) {
        var questions = new ArrayList<ChecklistQuestionModel>();
        list.forEach(item -> {
            ChecklistQuestionModel question = new ChecklistQuestionModel();
            question.setQuestion(item);
            question.setPassed(false);
            questionService.saveQuestion(question);
            questions.add(question);
        });
        return questions;
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
}
