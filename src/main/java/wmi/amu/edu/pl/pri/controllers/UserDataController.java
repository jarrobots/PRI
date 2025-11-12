package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wmi.amu.edu.pl.pri.dto.modeldto.UserDataDto;
import wmi.amu.edu.pl.pri.services.UserDataService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserDataController {
    @Autowired
    private UserDataService userDataService;

    @GetMapping("/users")
    public List<UserDataDto> getStudents(
    ) {
        return userDataService.getUsers();
    }
}
