package wmi.amu.edu.pl.pri.dto.view.timeline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wmi.amu.edu.pl.pri.dto.TimelineDefenceDateDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimelineViewChapterDto {
    private String name;
    private TimelineViewAuthorDto author;
    private TimelineDefenceDateDto defenseDate;
    private List<TimelineViewVersionDto> versions;
}
