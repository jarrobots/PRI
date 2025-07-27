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

    @RequestMapping(method=POST, path = "/files")
    public ResponseEntity<Integer> create(@RequestParam("file") MultipartFile file,@RequestParam("ownerId") Integer ownerId, @RequestParam("uploaderId") Integer uploaderId
    ) {

        ChapterVersionModel chapter = new ChapterVersionModel();
        int id = -1;
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
            chapter.setStudent(studentService.getStudentById(uploaderId));
            chapter.setOwner(studentService.getStudentById(ownerId));
            chapter.setDate(new Date());

            return ResponseEntity.ok().body(chapterService.saveFile(chapter));
        }
        else{
            return  ResponseEntity.badRequest().body(id);
        }
    }

    @RequestMapping(method = GET, path = "/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
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

    /*@RequestMapping(method = GET, path = "/view")
    public ResponseEntity<List<ChapterVersionModel>> getFiles(@RequestParam(value="id") Integer studentId) {
        StudentModel student = studentService.getStudentById(studentId);
        List<ChapterVersionModel> list = chapterService.findChapterFileModelByStudent(student);

        return ResponseEntity.ok().body(list);
    }*/





}