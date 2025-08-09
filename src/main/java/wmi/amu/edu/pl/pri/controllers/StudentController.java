package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wmi.amu.edu.pl.pri.dto.modeldto.StudentModelDto;
import wmi.amu.edu.pl.pri.services.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class StudentController {

    @Autowired
    private final StudentService studentService;

    @GetMapping("/students")
    public List<StudentModelDto> getStudents(
    ) {
        return studentService.findAllStudents();
    }
    @GetMapping("/view/groups/students")
    public List<StudentModelDto> getStudentsByGroup(@RequestParam(value="id") Long projectId){
        return studentService.findStudentsByProjectId(projectId);
    }
}
