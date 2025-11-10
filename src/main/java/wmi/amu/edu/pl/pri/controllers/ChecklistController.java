package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.services.ChecklistService;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChecklistController {
    @Autowired
    private ChecklistService checklistService;

    @RequestMapping(method=POST, path = "/post/note")
    public Boolean saveQuestions(
            @RequestBody ChecklistDto dto
    ){
        checklistService.setChecklist(dto);
        return true;
    }
    @RequestMapping( path = "/view/version/{id}/note")
    public ResponseEntity<ChecklistDto> getQuestionsByVersion(@PathVariable Long id)
    {
        return ResponseEntity.ok().body(checklistService.getChecklistByVersionId(id));
    }

    @RequestMapping( path = "/view/chapter/{id}/note")
    public ResponseEntity<ChecklistDto> getQuestionsByChapter(@PathVariable Long id)
    {
        return ResponseEntity.ok().body(checklistService.getChecklistByChapterId(id));
    }
}
