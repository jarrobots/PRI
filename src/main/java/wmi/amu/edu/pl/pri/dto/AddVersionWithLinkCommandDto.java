package wmi.amu.edu.pl.pri.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddVersionWithLinkCommandDto {

    private Long ownerUserDataId;
    private Long uploaderUserDataId;
    private String link;
}
