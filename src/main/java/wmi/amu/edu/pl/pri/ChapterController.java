package wmi.amu.edu.pl.pri;

import wmi.amu.edu.pl.pri.Chapters;
import wmi.amu.edu.pl.pri.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping("/")
    public ResponseEntity<List<Chapters>> getAllChapters(){
        return ResponseEntity.ok().body(chapterService.getAllChapters());
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getChapterById(@PathVariable Integer id)
    {
        return ResponseEntity.ok().body(chapterService.getChapterById(id).toString());
    }

    @PostMapping("/")
    public ResponseEntity<Chapters> saveChapter(@RequestBody Chapters chapters)
    {
        return ResponseEntity.ok().body(chapterService.saveChapter(chapters));
    }

    @PutMapping("/")
    public ResponseEntity<Chapters> updateChapter(@RequestBody Chapters chapters)
    {
        return ResponseEntity.ok().body(chapterService.updateChapters(chapters));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Integer id)
    {
        chapterService.deleteChaptersById(id);
        return ResponseEntity.ok().body("Deleted chapter successfully");
    }


}