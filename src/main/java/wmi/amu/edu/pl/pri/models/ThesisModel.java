package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wmi.amu.edu.pl.pri.dto.ThesisCompleteDto;
import wmi.amu.edu.pl.pri.dto.ThesisCoreDto;
import wmi.amu.edu.pl.pri.models.pri.ProjectModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "thm_thesis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThesisModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "description")
    private String description;

    @Column(name = "description_en")
    private String descriptionEn;

    @Column(name = "approval_status")
    private String approvalStatus;

    @Column(name = "supervisor_comment")
    private String supervisorComment;

    @OneToMany(mappedBy = "thesis", cascade = CascadeType.ALL)
    private List<ChapterModel> chapters = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false, unique = true)
    private ProjectModel project;

    public ThesisCompleteDto toCompleteDto(){
        return ThesisCompleteDto.builder()
                .id(this.id)
                .title(this.title)
                .titleEn(this.titleEn)
                .description(this.description)
                .descriptionEn(this.descriptionEn)
                .approvalStatus(this.approvalStatus)
                .supervisorComment(this.supervisorComment)
                .chapters(this.chapters != null ? this.chapters.stream()
                        .map(ChapterModel::toDto)
                        .collect(Collectors.toList()) : null)
                .projectId(this.project != null ? this.project.getId() : null)
                .build();
    }

    public void applyDataFrom(ThesisCoreDto coreDto) {
            setTitle(coreDto.getTitle());
            setTitleEn(coreDto.getTitleEn());
            setDescription(coreDto.getDescription());
            setDescriptionEn(coreDto.getDescriptionEn());
            setSupervisorComment(coreDto.getSupervisorComment());
        }
}

