package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wmi.amu.edu.pl.pri.dto.ThesisCompleteDto;
import wmi.amu.edu.pl.pri.dto.ThesisCoreDto;
import wmi.amu.edu.pl.pri.services.ThesisService;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ThesisController {

    private final ThesisService thesisService;

    @GetMapping("/{id}")
    public ResponseEntity<ThesisCompleteDto> getThesisById(@PathVariable Long id) {
        ThesisCompleteDto thesis = thesisService.findById(id);
        if (thesis == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(thesis);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThesisCompleteDto> updateThesis(@PathVariable Long id, @RequestBody ThesisCoreDto thesisCompleteDto) {
        ThesisCompleteDto updatedThesis = thesisService.update(id, thesisCompleteDto);

        if (updatedThesis == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(updatedThesis);
    }
}
