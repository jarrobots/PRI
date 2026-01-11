package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wmi.amu.edu.pl.pri.dto.*;
import wmi.amu.edu.pl.pri.dto.TimelineDefenceDateDto;
import wmi.amu.edu.pl.pri.models.ChapterModel;
import wmi.amu.edu.pl.pri.models.ChapterVersionModel;
import wmi.amu.edu.pl.pri.models.FileContentModel;
import wmi.amu.edu.pl.pri.models.FinalGradeModel;
import wmi.amu.edu.pl.pri.services.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChapterController {

    @Autowired
    private final VersionService versionService;
    private final FileContentService fileService;
    private final UserDataService userDataService;
    @Autowired
    private final ChapterService chapterService;
    @Autowired
    private final ThesisService thesisService;
    @Autowired
    private final FinalGradeService finalGradeService;
    @Autowired
    private DefenceDateService defenceDateService;

    @GetMapping("/view/version/byOwner/{id}")
    public ResponseEntity<ChapterVersionsDto> getVersionsByStudentId(
            @PathVariable Long id) {
        System.out.println("Dostano zapytanie.");
        Long chapterId = chapterService.findChapterByOwnerId(id).getId();
        return ResponseEntity.ok().body(versionService.getChapterVersionByChapterId(chapterId));
    }

    @PostMapping(path = "/file")
    public ResponseEntity<Long> create(@RequestParam("ownerId") List<Long> ownerList, @RequestParam("uploaderId") Long uploaderId, @RequestBody MultipartFile file
    ) {
        List<ChapterModel> userDataModelList = ownerList.stream()
                .map(chapterService::findChapterByOwnerId)
                .toList();

        ChapterVersionModel chapter = new ChapterVersionModel();
        long id = -1;
        try {
            Object obj = file.getBytes();
            System.out.println(obj.getClass());
            id = fileService.saveFile(file.getBytes(), file.getOriginalFilename(), file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (id != -1) {
            chapter.setName(file.getOriginalFilename());
            chapter.setFileId(id);
            chapter.setUploader(userDataService.getUserData(uploaderId));
            chapter.setChapters(userDataModelList);
            chapter.setDate(new Date());

            return ResponseEntity.ok().body(versionService.saveFile(chapter));
        } else {
            return ResponseEntity.badRequest().body(id);
        }
    }

    @PostMapping(path = "/chapter/addVersionWithLink")
    public ResponseEntity<Long> addVersion(@RequestParam("chapterIds") List<Long> chapterIdList, @RequestBody AddVersionWithLinkCommandDto commandDto) {
        if(chapterIdList.isEmpty()) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().body(versionService.saveVersion(chapterIdList, commandDto));
    }


    @GetMapping( path = "/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Optional<FileContentModel> fileOptional = fileService.getFileById(id);

        if (fileOptional.isPresent()) {
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

    @GetMapping("chapter/{id}")
    public ResponseEntity<ChapterCoreDto> getChapterById(@PathVariable Long id) {
        ChapterCoreDto chapter = chapterService.findById(id); // Throws 404 if not found
        return ResponseEntity.ok(chapter);
    }

    @PatchMapping("chapter/{id}")
    public ResponseEntity<ChapterCoreDto> updateChapter(@PathVariable Long id, @RequestBody ChapterCoreDto chapterDto) {
        ChapterCoreDto updatedChapter = chapterService.update(id, chapterDto);
        return ResponseEntity.ok(updatedChapter);
    }

    @PostMapping("chapter/{id}/approve")
    public ResponseEntity<ChapterCoreDto> approveChapter(@PathVariable Long id) {
        var approvedChapter = chapterService.confirm(id);
        return ResponseEntity.ok(approvedChapter);
    }

    @GetMapping("/chapter/{projectId}/all")
    public ResponseEntity<List<ChapterCoreDto>> getChaptersByProjectId(@PathVariable Long projectId) {
        var chapters = thesisService.findByProjectId(projectId).getChapters();

        if (chapters == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(chapters);
    }

    @GetMapping("/chapter/getGrade/{thesisId}")
    public ResponseEntity<FinalGradeDto> getFinalGrade(@PathVariable Long thesisId) {
        long chapterId = chapterService.getByThesisId(thesisId).get(0).getId();
        var dto = finalGradeService.getModelByChapterId(chapterId).toDto();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/chapter/addGrade")
     public ResponseEntity<Long> addFinalGrade(@RequestBody FinalGradeDto dto) {
        long thesisId = dto.getId();
        List<ChapterModel> chapterModels = chapterService.getByThesisId(thesisId);
        for(var chapterModel : chapterModels) {
            var model = new FinalGradeModel();
            ChapterModel chapter = chapterService.getById(chapterModel.getId());
            model.setChapter(chapter);
            model.setDescription(dto.getText());
            finalGradeService.setFinalGrade(model);
        }
        return ResponseEntity.ok(thesisId);
    }

    @GetMapping("chapter/getDefence/{chapterId}")
    public ResponseEntity<TimelineDefenceDateDto> getDefenceDate(@PathVariable Long chapterId) {
        return ResponseEntity.ok(defenceDateService.getDefenceDateByChapter(chapterId));
    }

    @PostMapping("chapter/addDefence")
    public ResponseEntity<Boolean> addDefenceDate(@RequestBody TimelineDefenceDateDto dto) {
        return ResponseEntity.ok(defenceDateService.saveDefenceDate(dto));
    }

}

