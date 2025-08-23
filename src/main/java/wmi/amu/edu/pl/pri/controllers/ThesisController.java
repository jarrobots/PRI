package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wmi.amu.edu.pl.pri.dto.ThesisCompleteDto;
import wmi.amu.edu.pl.pri.dto.ThesisCoreDto;
import wmi.amu.edu.pl.pri.services.ThesisService;

@RestController
@RequestMapping("/api/v1/thesis")
@RequiredArgsConstructor
public class ThesisController {

    private final ThesisService thesisService;

    @GetMapping("{id}")
    public ResponseEntity<ThesisCompleteDto> getThesisById(@PathVariable Long id) {
        ThesisCompleteDto thesis = thesisService.findById(id);
        if (thesis == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(thesis);
    }

    @GetMapping("/byProjectId/{id}")
    public ResponseEntity<ThesisCompleteDto> getThesisByProjectId(@PathVariable Long id) {
        ThesisCompleteDto thesis = thesisService.findByProjectId(id);
        if (thesis == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(thesis);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ThesisCompleteDto> updateThesis(@PathVariable Long id, @RequestBody ThesisCoreDto thesisCompleteDto) {
        ThesisCompleteDto updatedThesis = thesisService.update(id, thesisCompleteDto);

        if (updatedThesis == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(updatedThesis);
    }

    @PostMapping("{id}/approve")
    public ResponseEntity<ThesisCompleteDto> approveChapter(@PathVariable Long id){
        var approvedThesis = thesisService.confirm(id);
        return ResponseEntity.ok(approvedThesis);
    }
}
