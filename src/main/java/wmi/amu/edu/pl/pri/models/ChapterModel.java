package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wmi.amu.edu.pl.pri.dto.ChapterCoreDto;

import java.util.List;

import java.util.List;

@Entity
@Table(name = "thm_chapter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "description")
    private String description;

    @Column(name = "description_en")
    private String descriptionEn;

    //informacja o tym czy rozdzial zostal zaakceptowany podczas formulowania pracy
    @Column(name = "approval_status")
    private String approvalStatus;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "versions", referencedColumnName = "id")
    private List<ChapterVersionModel> versionModels;

    //tu ID tabeli student, w przyszlosci przechodzimy na user_data ze starego systemu
    @Column(name = "user_data_id")
    private Long userDataId;



    @Column(name = "supervisor_comment")
    private String supervisorComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thesis_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ThesisModel thesis;

    @OneToMany(fetch = FetchType.EAGER)
    private List<ChapterVersionModel> versions;

    public ChapterCoreDto toDto() {
        return ChapterCoreDto.builder()
                .id(this.id)
                .title(this.title)
                .titleEn(this.titleEn)
                .description(this.description)
                .descriptionEn(this.descriptionEn)
                .approvalStatus(this.approvalStatus)
                .userDataId(this.userDataId)
                .supervisorComment(this.supervisorComment)
                .thesisId(this.thesis != null ? this.thesis.getId() : null)
                .build();
    }

    public void applyDataFrom(ChapterCoreDto dto){
        setTitle(dto.getTitle());
        setTitleEn(dto.getTitleEn());
        setDescription(dto.getDescription());
        setDescriptionEn(dto.getDescriptionEn());
        setSupervisorComment(dto.getSupervisorComment());
    }
}
