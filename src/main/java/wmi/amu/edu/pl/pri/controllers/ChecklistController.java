package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.services.ChecklistService;
import wmi.amu.edu.pl.pri.services.UserChecklistTemplateService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChecklistController {
    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private UserChecklistTemplateService userChecklistTemplateService;

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

    @PostMapping(path = "/post/checklistTemplate/{userId}")
    public ResponseEntity<Boolean> saveChecklistTemplate(@PathVariable Long userId, @RequestBody List<String> list){
      userChecklistTemplateService.addChecklistTemplates(userId, list);
      return ResponseEntity.ok(true);
    }



}
