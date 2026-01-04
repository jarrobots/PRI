package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;
import wmi.amu.edu.pl.pri.dto.ThesisCompleteDto;
import wmi.amu.edu.pl.pri.dto.ThesisCoreDto;
import wmi.amu.edu.pl.pri.models.pri.ProjectModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "thm_thesis")
@Getter
@Setter
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

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "description_en", columnDefinition = "text")
    private String descriptionEn;

    @Column(name = "approval_status")
    private String approvalStatus;

    @Column(name = "supervisor_comment", columnDefinition = "text")
    private String supervisorComment;

    @Column(name = "defence_date")
    private Date defenceDate;

    @OneToMany(mappedBy = "thesis", cascade = CascadeType.ALL)
    private List<ChapterModel> chapters = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false, unique = true)
    private ProjectModel project;

    @Column(name = "review", columnDefinition = "text")
    private String review;

    public ThesisCompleteDto toCompleteDto(){
        //review field is omitted because buisness requirement specify it to be not available for students
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

    public List<ChapterVersionModel> summarizeVersionsAsFlatList() {
        return chapters.stream()
                .flatMap(chapterModel -> chapterModel.getVersions().stream())
                .toList();
    }
}

