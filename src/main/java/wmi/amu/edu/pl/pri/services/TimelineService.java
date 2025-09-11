package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wmi.amu.edu.pl.pri.domainobject.TimelineMapper;
import wmi.amu.edu.pl.pri.dto.view.timeline.TimelineViewDto;

@Service
@RequiredArgsConstructor
public class TimelineService {

    @Autowired
    private ThesisService thesisService;

    @Autowired
    private ChecklistService checklistService;

    @Value("${server.port}")
    private String currentPort;

    @Value("${spring.profiles.active:}")
    private String activeProfile;



    public TimelineViewDto getTimelineViewDto(Long thesisId){
        var thesis = thesisService.findById(thesisId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Thesis not found with ID: " + thesisId + ", so no data for timeline available"));
        var versionSummary = thesis.summarizeVersionsAsFlatList();
        var tallies = checklistService.getChecklistTalliesByThesis(thesis);
        TimelineMapper timelineMapper = new TimelineMapper(thesis, currentPort, activeProfile, tallies);
        return timelineMapper.toTimeLineViewDto();
    }



}
