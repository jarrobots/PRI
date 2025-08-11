package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.services.ChecklistService;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChecklistController {
    @Autowired
    private final ChecklistService checklistService;
    @RequestMapping(method=POST, path = "/post/note")
    public Boolean saveQuestions(
            @RequestParam(value="checklistDto") ChecklistDto dto
    ){
        checklistService.setChapterlist(dto);
        return true;
    }
    @RequestMapping( path = "/view/note")
    public ResponseEntity<ChecklistDto> getQuestions(
            @RequestParam(value="id") Integer id
    ){
        return ResponseEntity.ok().body(checklistService.getChecklistByStudentId(id));
    }

}
