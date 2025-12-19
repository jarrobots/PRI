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
    private final UserChecklistTemplateService userTemplateService;
    private final ChapterChecklistTemplateService chapterTemplateService;
    private final ChapterService chapterService;

    public ChecklistDto getChecklistByVersionId(Long id, Long userId) {
        Optional<ChecklistModel> optional = repo.findByVersionId(id);
        if (optional.isEmpty()) {
            var list = userTemplateService.getChecklistTemplates(userId);
            var model = generateVersionChecklistFromDB(list, userId);
            return model.toChecklistDto();
        }
        return optional.get().toChecklistDto();
    }

    public ChecklistDto getChecklistByChapterId(Long chapterId) {
        Optional<ChecklistModel> optional = repo.findByChapterId(chapterId);
        if (optional.isEmpty()) {
            Long ownerId = chapterService.getById(chapterId).getOwner().getId();
            var list = chapterTemplateService.getChecklistTemplates(chapterId);
            var model = generateChapterChecklistFromDB(list, ownerId);
            return model.toChecklistDto();

        }
        return optional.get().toChecklistDto();
    }

    public void setChecklist(ChecklistDto dto) {
        Optional<ChecklistModel> optional = repo.findByVersionId(dto.getVersionId());
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

    private ChecklistModel generateVersionChecklistFromDB(List<String> list, Long userId) {
        var questions = generateQuestions(list);
        var model  = new ChecklistModel();
        model.setChecklistQuestionModels(questions);

        Long id = chapterService.findChapterByOwnerId(userId).getId();
        model.setVersionModel(versionService.getChapterVersionById(id));
        model.setDate(new Date());
        repo.save(model);

        return model;
    }

    private ChecklistModel generateChapterChecklistFromDB(List<String> list, Long chapterId) {
        var questions = generateQuestions(list);
        var model  = new ChecklistModel();
        model.setChecklistQuestionModels(questions);
        model.setChapterModel(chapterService.getById(chapterId));
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
