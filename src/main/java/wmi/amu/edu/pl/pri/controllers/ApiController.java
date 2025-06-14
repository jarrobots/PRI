package wmi.amu.edu.pl.pri.controllers;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wmi.amu.edu.pl.pri.models.ChapterFileModel;
import wmi.amu.edu.pl.pri.models.FileContentModel;
import wmi.amu.edu.pl.pri.models.StudentModel;
import wmi.amu.edu.pl.pri.services.ChapterFileService;
import wmi.amu.edu.pl.pri.services.FileContentService;
import wmi.amu.edu.pl.pri.services.StudentService;

import java.io.IOException;
import java.text.DateFormat;
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
    public ResponseEntity<Integer> create(@RequestParam("file") MultipartFile file){

        ChapterFileModel chapter = new ChapterFileModel();
        int id = -1;
        try {
            Object obj = file.getBytes();
            System.out.println(obj.getClass());
            id = fileService.saveFile(file.getBytes(),file.getOriginalFilename(),file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(id != -1) {
            chapter.setName(file.getName());
            chapter.setFileId(id);
            chapter.setStudent(studentService.getStudentById(1));
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
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + file.getFileName() + "\"");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(file.getFileData());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @RequestMapping(method = GET, path = "/view/{id}")
    public ResponseEntity<List<ChapterFileModel>> getFiles(@PathVariable Integer id) {
        StudentModel student = studentService.getStudentById(id);
         List<ChapterFileModel> list = chapterService.findChapterFileModelByStudent(student);

         return ResponseEntity.ok().body(list);
    }


    @GetMapping("/student")
    public void setUser(){
        StudentModel student = new StudentModel();
        student.setFName("zzz");
        student.setLName("ZZZ");
        student.setEmail("zzz@st.amu.edu.pl");
        studentService.saveStudent(student);
    }

    @GetMapping("/")
    public ResponseEntity<List<ChapterFileModel>> getAllChapters(){
        return ResponseEntity.ok().body(chapterService.getAllFiles());
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getChapterById(@PathVariable Integer id)
    {
        return ResponseEntity.ok().body(chapterService.getFileById(id).toString());
    }

    @PostMapping("/")
    public ResponseEntity<Integer> saveChapter(@RequestBody ChapterFileModel chapters)
    {
        return ResponseEntity.ok().body(chapterService.saveFile(chapters));
    }

    @PutMapping("/")
    public ResponseEntity<ChapterFileModel> updateChapter(@RequestBody ChapterFileModel chapters)
    {
        return ResponseEntity.ok().body(chapterService.updateFile(chapters));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Integer id)
    {
        chapterService.deleteFileById(id);
        return ResponseEntity.ok().body("Deleted chapter successfully");
    }


}