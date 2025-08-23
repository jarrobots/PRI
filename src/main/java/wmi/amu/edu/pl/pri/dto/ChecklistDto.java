package wmi.amu.edu.pl.pri.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wmi.amu.edu.pl.pri.models.ChecklistQuestionModel;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChecklistDto {
    private Long id;
    private Date uploadTime;
    private boolean isPassed;
    private Long versionId;
    private List<ChecklistQuestionModel> models;
}
