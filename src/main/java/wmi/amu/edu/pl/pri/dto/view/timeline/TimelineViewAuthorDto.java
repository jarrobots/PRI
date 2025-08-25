package wmi.amu.edu.pl.pri.dto.view.timeline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimelineViewAuthorDto {
    private Long userDataId;
    private String userDataFirstName;
    private String userDataLastName;
    private String userDataEmail;
}
