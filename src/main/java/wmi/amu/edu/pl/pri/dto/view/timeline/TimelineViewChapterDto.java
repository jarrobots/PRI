package wmi.amu.edu.pl.pri.dto.view.timeline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimelineViewChapterDto {
    private String name;
    private TimelineViewAuthorDto author;
    private List<TimelineViewVersionDto> versions;
}
