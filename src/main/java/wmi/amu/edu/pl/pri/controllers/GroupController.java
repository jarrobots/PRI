package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wmi.amu.edu.pl.pri.dto.GroupDto;
import wmi.amu.edu.pl.pri.dto.GroupsDto;
import wmi.amu.edu.pl.pri.dto.ReloadGroupsCommandDto;
import wmi.amu.edu.pl.pri.services.GroupService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GroupController {

    @Autowired
    private final GroupService groupService;

    @GetMapping("/view/group")
    public GroupDto getGroupByStudentId(
            @RequestParam(value="studentId") Long studentId
    ){

        return groupService.getGroupById(studentId);
    }

    @GetMapping("/view/groups")
    public GroupsDto getGroups(
            @RequestParam(value="id") Long supervisorId
    ){
        return groupService.getGetGroupsBySupervisorId(supervisorId);
    }

    @GetMapping("/view/groups/all")
    public GroupsDto getAll(
    ){

        return groupService.findAll();
    }

    @PostMapping("/reloadGroups")
    public ResponseEntity<?> reloadGroups(@RequestBody ReloadGroupsCommandDto request) {

        groupService.reloadGroups(request.supervisordUserDataId());

        return ResponseEntity.ok("Groups reloaded successfully");
    }
}
