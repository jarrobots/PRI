package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wmi.amu.edu.pl.pri.models.ChapterVersionModel;
import wmi.amu.edu.pl.pri.models.FileContentModel;
import wmi.amu.edu.pl.pri.models.StudentModel;
import wmi.amu.edu.pl.pri.services.ChapterFileService;
import wmi.amu.edu.pl.pri.services.FileContentService;
import wmi.amu.edu.pl.pri.services.StudentService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class ApiController {

    private final StudentService studentService;
    private final ChapterFileService chapterService;
    private final FileContentService fileService;





    /*@RequestMapping(method = GET, path = "/view")
    public ResponseEntity<List<ChapterVersionModel>> getFiles(@RequestParam(value="id") Integer studentId) {
        StudentModel student = studentService.getStudentById(studentId);
        List<ChapterVersionModel> list = chapterService.findChapterFileModelByStudent(student);

        return ResponseEntity.ok().body(list);
    }*/





}