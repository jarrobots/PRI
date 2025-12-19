package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.services.ChapterChecklistTemplateService;
import wmi.amu.edu.pl.pri.services.ChecklistService;
import wmi.amu.edu.pl.pri.services.UserChecklistTemplateService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChecklistController {
    private ChecklistService checklistService;
    private UserChecklistTemplateService userChecklistTemplateService;
    private ChapterChecklistTemplateService chapterChecklistTemplateService;

    @PostMapping(path = "/post/note")
    public ResponseEntity<Boolean> saveQuestions(
            @RequestBody ChecklistDto dto
    ){
        checklistService.setChecklist(dto);
        return ResponseEntity.ok(true);
    }

    @GetMapping( path = "/view/version/{id}/note/{userId}")
    public ResponseEntity<ChecklistDto> getQuestionsByVersion(@PathVariable Long id,  @PathVariable Long userId)
    {
        return ResponseEntity.ok().body(checklistService.getChecklistByVersionId(id, userId));
    }

    @GetMapping( path = "/view/chapter/{id}/note")
    public ResponseEntity<ChecklistDto> getQuestionsByChapter(@PathVariable Long id)
    {
        return ResponseEntity.ok().body(checklistService.getChecklistByChapterId(id));
    }

    @PostMapping(path = "/post/checklistTemplate/user/{userId}")
    public ResponseEntity<Boolean> saveChecklistTemplate(@PathVariable Long userId, @RequestBody List<String> list){
      userChecklistTemplateService.addChecklistTemplates(userId, list);
      return ResponseEntity.ok(true);
    }
    @PostMapping(path = "/post/checklistTemplate/chapter/{chapterId}")
    public ResponseEntity<Boolean> saveChapterChecklistTemplate(@PathVariable Long chapterId, @RequestBody List<String> list){
        chapterChecklistTemplateService.addChecklistTemplates(chapterId, list);
        return ResponseEntity.ok(true);
    }



}
