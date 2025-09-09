package wmi.amu.edu.pl.pri.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TimelineChecklistTallyDto {

    private Integer total;
    private Integer resolved;
}
