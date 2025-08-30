package wmi.amu.edu.pl.pri.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ThesisCompleteDto {
    private Long id;
    private String title;
    private String titleEn;
    private String description;
    private String descriptionEn;
    private String approvalStatus;
    private String supervisorComment;
    private List<ChapterCoreDto> chapters;
    private Long projectId;
}