package wmi.amu.edu.pl.pri.dto.view.timeline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wmi.amu.edu.pl.pri.dto.TimelineChecklistTallyDto;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimelineViewVersionDto {
    private Long id;
    private TimelineViewUploaderDto uploader;
    private Date uploadDateTime;
    private TimelineChecklistTallyDto checklistTally;
    private String supervisorComment;
    private String fileLink;
}
