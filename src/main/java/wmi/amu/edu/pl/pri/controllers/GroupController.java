package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wmi.amu.edu.pl.pri.dto.ChapterVersionsDto;
import wmi.amu.edu.pl.pri.models.GroupModel;
import wmi.amu.edu.pl.pri.services.GroupService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GroupController {

    @Autowired
    private final GroupService groupService;

    @GetMapping("/view/group")
    public GroupModel getVersionsByStudentId(
            @RequestParam(value="studentId") Integer studentId
    ){

        return groupService.getGroupById(studentId);
    }


}
