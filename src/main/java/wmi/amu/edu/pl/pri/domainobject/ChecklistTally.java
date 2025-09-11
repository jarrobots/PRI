package wmi.amu.edu.pl.pri.domainobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wmi.amu.edu.pl.pri.dto.TimelineChecklistTallyDto;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChecklistTally {

    private Long chapterVersionId;
    private Integer total;
    private Integer resolved;

    public TimelineChecklistTallyDto toDto(){
        return TimelineChecklistTallyDto.builder()
                .total(total)
                .resolved(resolved)
                .build();
    }
}
