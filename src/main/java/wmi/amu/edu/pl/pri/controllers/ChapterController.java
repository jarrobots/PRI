package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wmi.amu.edu.pl.pri.dto.ChapterVersionsDto;
import wmi.amu.edu.pl.pri.models.ChapterVersionModel;
import wmi.amu.edu.pl.pri.models.FileContentModel;
import wmi.amu.edu.pl.pri.services.FileContentService;
import wmi.amu.edu.pl.pri.services.StudentService;
import wmi.amu.edu.pl.pri.services.UserDataService;
import wmi.amu.edu.pl.pri.services.VersionService;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChapterController {

    @Autowired
    private final VersionService versionService;
    private final FileContentService fileService;
    private final UserDataService userDataService;

    @GetMapping("/view")
    public ResponseEntity<ChapterVersionsDto> getVersionsByStudentId(
            @RequestParam(value="id") Long studentId
    ){
        return ResponseEntity.ok().body(versionService.getChapterVersionsByOwnerId(studentId));
        //return versionService.getChapterVersionsByStudentId(studentId);
    }
    @RequestMapping(method=POST, path = "/files")
    public ResponseEntity<Long> create(@RequestParam("file") MultipartFile file, @RequestParam("ownerId") Long ownerId, @RequestParam("uploaderId") Long uploaderId
    ) {

        ChapterVersionModel chapter = new ChapterVersionModel();
        long id = -1;
        try {
            Object obj = file.getBytes();
            System.out.println(obj.getClass());
            id = fileService.saveFile(file.getBytes(),file.getOriginalFilename(),file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(id != -1) {
            chapter.setName(file.getOriginalFilename());
            chapter.setFileId(id);
            chapter.setUploader(userDataService.getUserData(uploaderId));
            chapter.setDate(new Date());

            return ResponseEntity.ok().body(versionService.saveFile(chapter));
        }
        else{
            return  ResponseEntity.badRequest().body(id);
        }
    }
    @RequestMapping(method = GET, path = "/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Optional<FileContentModel> fileOptional = fileService.getFileById(id);

        if(fileOptional.isPresent()){
            FileContentModel file = fileOptional.get();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=\"%s\"", file.getFileName()));
            headers.setContentType(MediaType.valueOf(file.getFileType()));
            headers.setContentLength(file.getFileData().length);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(file.getFileData());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
