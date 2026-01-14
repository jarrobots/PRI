package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.domainobject.ChecklistTally;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.models.*;
import wmi.amu.edu.pl.pri.repositories.ChecklistRepo;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChecklistService {
    private final ChecklistRepo repo;
    private final VersionService versionService;
    private final ChecklistQuestionService questionService;
    private final ThesisChecklistTemplateService thesisTemplateService;
    private final ChapterChecklistTemplateService chapterTemplateService;
    private final ThesisService thesisService;

    public ChecklistDto getChecklistByVersionId(Long id) {
        Optional<ChecklistModel> optional = repo.findByVersionId(id);
        if (optional.isEmpty()) {
            var list = chapterTemplateService.getChecklistTemplates();
            var model = generateVersionChecklistFromDB(list, id);
            return model.toChecklistDto();
        }
        var model = optional.get();
        return model.toChecklistDto();

    }


    public ChecklistDto getChecklistByThesisId(Long thesisId) {
        // ✅ ZMIANA 1: uprość orElseGet
        ChecklistModel model = repo.findByThesisId(thesisId)
                .orElseGet(() -> generateThesisChecklistFromDB(thesisTemplateService.getChecklistTemplates(), thesisId));

        var existingQuestions = new ArrayList<>(model.getChecklistQuestionModels());

        // Znajdź różnice (bez zmian)
        var currentQuestions = existingQuestions.stream()
                .map(ChecklistQuestionModel::getQuestion).collect(toList());
        var templateQuestions = thesisTemplateService.getChecklistTemplates();

        var addList = findDifferenceInLists(templateQuestions, currentQuestions);
        var delList = findDifferenceInLists(currentQuestions, templateQuestions);

        if (!addList.isEmpty() || !delList.isEmpty()) {
            // ✅ ZMIANA 2: USUŃ Z MODELU (NPE fix!)
            model.getChecklistQuestionModels().removeIf(q ->
                    delList.stream().anyMatch(dl -> dl.getQuestion().equals(q.getQuestion())));

            // 1. USUŃ stare (bez zmian)
            deleteQuestions(delList, model);
            System.out.println(delList.size() + " questions were deleted");

            // 2. DODAJ nowe (bez zmian)
            addList = saveQuestions(addList);
            existingQuestions.addAll(addList);

            // 3. Zapisz (uprość)
            model.getChecklistQuestionModels().clear();
            model.getChecklistQuestionModels().addAll(existingQuestions);
            repo.save(model);  // ✅ ZMIANA 3: usuń saveChcecklist()

            return model.toChecklistDto();
        }

        return model.toChecklistDto();
    }
    private List<ChecklistQuestionModel> saveQuestions(List<ChecklistQuestionModel> questions) {
        return questions.stream()
                .map(questionService::saveQuestion)
                .toList();
    }
    private List<ChecklistQuestionModel> findDifferenceInLists(List<String> list1, List<String> list2) {
        Set<String> set2 = new HashSet<>(list2);
        return list1.stream()
                .filter(item -> !set2.contains(item))
                .map(question -> new ChecklistQuestionModel(null, question, false))
                .toList();
    }

    private void deleteQuestions(List<ChecklistQuestionModel> list, ChecklistModel model) {
        Set<String> questionsToDelete = list.stream()
                .map(ChecklistQuestionModel::getQuestion)
                .collect(Collectors.toSet());

        model.getChecklistQuestionModels().stream()
                .filter(q -> questionsToDelete.contains(q.getQuestion()))
                .forEach(q -> questionService.deleteQuestion(q.getId()));
    }


    public void setChecklist(ChecklistDto dto) {
        ChecklistModel model;

        if (dto.getId() != null) {
            Optional<ChecklistModel> byId = repo.findById(dto.getId());
            if (byId.isPresent()) {
                model = byId.get();
                saveChcecklist(model, dto);
                return;
            }
        }

        if (dto.getThesisId() != null && repo.findByThesisId(dto.getThesisId()).isPresent()) {
            model = repo.findByThesisId(dto.getThesisId()).get();
            saveChcecklist(model, dto);
            return;
        }

        if (dto.getVersionId() != null && repo.findByVersionId(dto.getVersionId()).isPresent()) {
            model = repo.findByVersionId(dto.getVersionId()).get();
            saveChcecklist(model, dto);
            return;
        }

        throw new RuntimeException("Checklist not defined properly");
    }

    private void saveChcecklist(ChecklistModel model, ChecklistDto dto) {
        model.setDate(new Date());
        model.setChecklistQuestionModels(dto.getModels());
        for (ChecklistQuestionModel qModel : model.getChecklistQuestionModels()) {
            if(qModel.getId()==null){
                qModel.setId(questionService.saveQuestion(qModel).getId());
            }
            try {
                ChecklistQuestionModel question = questionService.getQuestion(qModel);
                question.setPassed(qModel.isPassed());
                questionService.saveQuestion(question);
            }
            catch(NoSuchElementException e){
                log.warn("brak question");
            }
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
