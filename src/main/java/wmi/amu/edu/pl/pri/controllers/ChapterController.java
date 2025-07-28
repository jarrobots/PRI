package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wmi.amu.edu.pl.pri.dto.ChapterDto;
import wmi.amu.edu.pl.pri.dto.ChapterVersionsDto;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.services.ChapterService;
import wmi.amu.edu.pl.pri.services.VersionService;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChapterController {

    @Autowired
    private final VersionService versionService;
    private final ChapterService chapterService;

    @GetMapping("/view")
    public ResponseEntity<ChapterVersionsDto> getVersionsByStudentId(
            @RequestParam(value="id") Integer studentId
    ){
        return ResponseEntity.ok().body(versionService.getChapterVersionsByStudentId(studentId));
        //return versionService.getChapterVersionsByStudentId(studentId);
    }
}
