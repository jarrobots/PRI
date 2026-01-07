package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.services.ChapterChecklistTemplateService;
import wmi.amu.edu.pl.pri.services.ChecklistService;
import wmi.amu.edu.pl.pri.services.ThesisChecklistTemplateService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChecklistController {
    private final ChecklistService checklistService;
    private final ThesisChecklistTemplateService thesisChecklistTemplateService;
    private final ChapterChecklistTemplateService chapterChecklistTemplateService;

    @PostMapping(path = "/post/note")
    public ResponseEntity<Boolean> saveQuestions(
            @RequestBody ChecklistDto dto
    ){
        checklistService.setChecklist(dto);
        return ResponseEntity.ok(true);
    }

    @GetMapping( path = "/view/thesis/{id}/note/")
    public ResponseEntity<ChecklistDto> getQuestionsByVersion(@PathVariable Long id)
    {
        return ResponseEntity.ok().body(checklistService.getChecklistByThesisId(id));
    }

    @GetMapping( path = "/view/version/{id}/note/")
    public ResponseEntity<ChecklistDto> getQuestionsByChapter(@PathVariable Long id)
    {
        return ResponseEntity.ok().body(checklistService.getChecklistByVersionId(id));
    }

    @PostMapping(path = "/post/thesisChecklistTemplate/")
    public ResponseEntity<Boolean> saveChecklistTemplate(@RequestBody List<String> list){
      thesisChecklistTemplateService.addChecklistTemplates(list);
      return ResponseEntity.ok(true);
    }
    @PostMapping(path = "/post/chapterChecklistTemplate/")
    public ResponseEntity<Boolean> saveChapterChecklistTemplate( @RequestBody List<String> list){
        chapterChecklistTemplateService.addChecklistTemplates(list);
        return ResponseEntity.ok(true);
    }



}
