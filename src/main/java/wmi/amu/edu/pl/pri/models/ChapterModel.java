package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;
import wmi.amu.edu.pl.pri.dto.ChapterCoreDto;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

import java.util.ArrayList;
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

    @Column(name = "description")
    private String description;

    @Column(name = "description_en")
    private String descriptionEn;

    //informacja o tym czy rozdzial zostal zaakceptowany podczas formulowania pracy
    @Column(name = "approval_status")
    private String approvalStatus;

    @OneToOne
    @JoinColumn(name = "owner_user_data_id", referencedColumnName = "id", nullable = false, unique = true)
    private UserDataModel owner;

    @Column(name = "supervisor_comment")
    private String supervisorComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thesis_id", referencedColumnName = "id")
    private ThesisModel thesis;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chapter")
    private List<ChapterVersionModel> versions = new ArrayList<>();

    public ChapterCoreDto toDto() {
        return ChapterCoreDto.builder()
                .id(this.id)
                .title(this.title)
                .titleEn(this.titleEn)
                .description(this.description)
                .descriptionEn(this.descriptionEn)
                .approvalStatus(this.approvalStatus)
                .userDataId(this.owner.getId()) // later change name in dto and on frontend
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
