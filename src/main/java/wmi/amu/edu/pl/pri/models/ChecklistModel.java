package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "thm_checklist")
public class ChecklistModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private boolean isPassed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version", referencedColumnName = "id")
    private ChapterVersionModel versionModel;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist", referencedColumnName = "id")
    private List<ChecklistQuestionModel> checklistQuestionModels;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    public ChecklistDto toChecklistDto(){
        return ChecklistDto.builder()
                .id(id)
                .versionId(versionModel.getId())
                .uploadTime(date)
                .models(checklistQuestionModels)
                .isPassed(isPassed)
                .build();
    }
}
