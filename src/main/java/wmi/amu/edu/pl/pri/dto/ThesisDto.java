package wmi.amu.edu.pl.pri.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ThesisDto {
    private Long id;
    private String title;
    private String titleEn;
    private String description;
    private String descriptionEn;
    private String approvalStatus;
    private String supervisorComment;
    private List<ChapterDto> chapters;
    private Long groupId;
    private Long projectId;
}