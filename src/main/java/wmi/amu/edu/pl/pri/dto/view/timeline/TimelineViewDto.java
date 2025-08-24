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
public class TimelineViewDto {
    private Long supervisorUserDataId;
    private Long supervisorId;
    private List<TimelineViewChapterDto> chapters;
}
