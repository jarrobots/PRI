package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wmi.amu.edu.pl.pri.dto.view.timeline.TimelineViewDto;
import wmi.amu.edu.pl.pri.services.TimelineService;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class TimelineController {

    @Autowired
    private TimelineService service;

    @GetMapping("timeline/view/byThesisId/{id}")
    public TimelineViewDto getTimelineData(@PathVariable Long id){
        return service.getTimelineViewDto(id);
    }
}
