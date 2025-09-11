package wmi.amu.edu.pl.pri.domainobject;

import wmi.amu.edu.pl.pri.dto.TimelineChecklistTallyDto;
import wmi.amu.edu.pl.pri.dto.view.timeline.*;
import wmi.amu.edu.pl.pri.models.ChapterModel;
import wmi.amu.edu.pl.pri.models.ChapterVersionModel;
import wmi.amu.edu.pl.pri.models.ThesisModel;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimelineMapper {

    private final ThesisModel thesisModel;
    private final String currentPort;
    private final List<ChecklistTally> checklistTallies;

    public TimelineMapper(ThesisModel thesis, String currentPort) {
        this.thesisModel = thesis;
        this.currentPort = currentPort;
        this.checklistTallies = new ArrayList<>();

    }

    public TimelineMapper(ThesisModel thesis, String currentPort, List<ChecklistTally> tallies) {
        this.thesisModel = thesis;
        this.currentPort = currentPort;
        this.checklistTallies = List.copyOf(tallies);

    }

    public TimelineViewDto toTimeLineViewDto() {

        Long supervisorUserDataId = null;
        Long supervisorId = null;
        if (thesisModel.getProject().getSupervisor() != null){
            supervisorUserDataId = thesisModel.getProject().getSupervisor().getUserData().getId();
            supervisorId = thesisModel.getProject().getSupervisor().getId();
        }

        List<TimelineViewChapterDto> chapters = thesisModel.getChapters()
                .stream()
                .map(this::toTimelineViewChapterDto)
                .collect(Collectors.toList());

        return TimelineViewDto.builder()
                .supervisorUserDataId(supervisorUserDataId)
                .supervisorId(supervisorId)
                .chapters(chapters)
                .build();
    }

    private TimelineViewChapterDto toTimelineViewChapterDto(ChapterModel chapter) {

        TimelineViewAuthorDto author = toTimelineViewAuthorDto(chapter.getOwner());
        List<TimelineViewVersionDto> versions = chapter.getVersions()
                .stream()
                .map(this::toTimelineViewVersionDto)
                .collect(Collectors.toList());

        return TimelineViewChapterDto.builder()
                .name(chapter.getTitle())
                .author(author)
                .versions(versions)
                .build();
    }

    private TimelineViewAuthorDto toTimelineViewAuthorDto(UserDataModel owner) {
        if (owner == null) {
            return TimelineViewAuthorDto.builder().build();
        }

        return TimelineViewAuthorDto.builder()
                .userDataId(owner.getId())
                .userDataFirstName(owner.getFirstName())
                .userDataLastName(owner.getLastName())
                .userDataEmail(owner.getEmail())
                .build();
    }

    private TimelineViewVersionDto toTimelineViewVersionDto(ChapterVersionModel version) {

        TimelineViewUploaderDto uploader = toTimelineViewUploaderDto(version.getUploader());
        String fileLink = version.getFormattedLink(currentPort);

        return TimelineViewVersionDto.builder()
                .id(version.getId())
                .uploader(uploader)
                .uploadDateTime(version.getDate())
                .checklistTally(checklistTallies.stream()
                        .filter(tally -> tally.getChapterVersionId().equals(version.getId()))
                        .findFirst()
                        .map(ChecklistTally::toDto)
                        .orElse(TimelineChecklistTallyDto.builder()
                                .total(0)
                                .resolved(0)
                                .build()))
                .supervisorComment("n/a")
                .fileLink(fileLink)
                .build();
    }

    private TimelineViewUploaderDto toTimelineViewUploaderDto(UserDataModel uploader) {
        if (uploader == null) {
            return TimelineViewUploaderDto.builder().build();
        }

        return TimelineViewUploaderDto.builder()
                .userDataId(uploader.getId())
                .userDataFirstName(uploader.getFirstName())
                .userDataLastName(uploader.getLastName())
                .build();
    }

    public void addChecklistTally(ChecklistTally checklistTally){
        checklistTallies.add(checklistTally);
    }
}