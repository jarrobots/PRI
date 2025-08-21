package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
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




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public ChapterVersionModel getVersionModel() {
        return versionModel;
    }

    public void setVersionModel(ChapterVersionModel versionModel) {
        this.versionModel = versionModel;
    }

    public List<ChecklistQuestionModel> getChecklistQuestionModels() {
        return checklistQuestionModels;
    }

    public void setChecklistQuestionModels(List<ChecklistQuestionModel> checklistQuestionModels) {
        this.checklistQuestionModels = checklistQuestionModels;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ChecklistDto toChecklistDto(){
        return ChecklistDto.builder()
                .versionId(versionModel.getId())
                .uploadTime(date)
                .models(checklistQuestionModels)
                .isPassed(isPassed)
                .build();
    }
}
