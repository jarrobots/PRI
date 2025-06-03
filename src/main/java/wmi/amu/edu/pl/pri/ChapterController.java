package wmi.amu.edu.pl.pri;

import wmi.amu.edu.pl.pri.Chapters;
import wmi.amu.edu.pl.pri.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class is where all the user requests are handled and required/appropriate
 * responses are sent
 */
@RestController
@RequestMapping("/employee/v1")
@RequiredArgsConstructor
@Validated
public class ChapterController {

    private final ChapterService chapterService;

    /**
     * This method is called when a GET request is made
     * URL: localhost:8080/employee/v1/
     * Purpose: Fetches all the employees in the employee table
     * @return List of Employees
     */
    @GetMapping("/")
    public ResponseEntity<List<Chapters>> getAllEmployees(){
        return ResponseEntity.ok().body(chapterService.getAllChapters());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Chapters> getChapterById(@PathVariable Integer id)
    {
        return ResponseEntity.ok().body(chapterService.getChapterById(id));
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

    /**
     * This method is called when a PUT request is made
     * URL: localhost:8080/employee/v1/1 (or any other id)
     * Purpose: Delete an Employee entity
     * @param id - employee's id to be deleted
     * @return a String message indicating employee record has been deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Integer id)
    {
        chapterService.deleteChaptersById(id);
        return ResponseEntity.ok().body("Deleted employee successfully");
    }


}