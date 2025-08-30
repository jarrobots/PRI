package wmi.amu.edu.pl.pri.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ThesisCoreDto {

    private Long id;
    private String title;
    private String titleEn;
    private String description;
    private String descriptionEn;
    private String supervisorComment;

}
