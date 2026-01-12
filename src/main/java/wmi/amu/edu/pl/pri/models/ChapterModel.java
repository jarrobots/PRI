package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wmi.amu.edu.pl.pri.dto.ChapterCoreDto;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

import java.util.List;

@Entity
@Table(name = "thm_chapter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChapterModel {
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

    @OneToOne
    @JoinColumn(name = "owner_user_data_id", referencedColumnName = "id", nullable = false)
    private UserDataModel owner;

    @Column(name = "supervisor_comment")
    private String supervisorComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thesis_id", referencedColumnName = "id", nullable = false)
    private ThesisModel thesis;

    @ManyToMany(mappedBy = "chapters")
    private List<ChapterVersionModel> versions;

    @OneToOne
    @JoinColumn(name = "defence_date_id", referencedColumnName = "id", unique = true)
    private DefenceDateModel defenceDate;

    public ChapterCoreDto toDto() {

        return ChapterCoreDto.builder()
                .id(this.id)
                .title(this.title)
                .titleEn(this.titleEn)
                .description(this.description)
                .descriptionEn(this.descriptionEn)
                .approvalStatus(this.approvalStatus)
                .ownerId(this.owner.getId()) // later change name in dto and on frontend
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
