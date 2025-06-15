package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wmi.amu.edu.pl.pri.dto.ChapterVersionsDto;
import wmi.amu.edu.pl.pri.services.VersionService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChapterVersionController {

    @Autowired
    private final VersionService versionService;

    @GetMapping("/chapterVersions")
    public ChapterVersionsDto getVersionsByStudentId(
            @RequestParam(value="studentId") Integer studentId
    ){

        return versionService.getChapterVersionsByStudentId(studentId);
    }

}
