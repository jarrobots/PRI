package wmi.amu.edu.pl.pri.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChapterCoreDto {
    private Long id;
    private String title;
    private String titleEn;
    private String description;
    private String descriptionEn;
    private String approvalStatus;
    private Long userDataId;
    private String supervisorComment;
    private Long thesisId;
}
