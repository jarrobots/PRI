package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;
import wmi.amu.edu.pl.pri.dto.ChapterCoreDto;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @ManyToMany
    @JoinTable(
            name = "chapter_owner", // name of the join table
            joinColumns = @JoinColumn(name = "chapter_id"), // foreign key column referencing ChapterModel
            inverseJoinColumns = @JoinColumn(name = "user_id") // foreign key column referencing UserDataModel
    )
    private List<UserDataModel> owners;

    @Column(name = "supervisor_comment")
    private String supervisorComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thesis_id", referencedColumnName = "id")
    private ThesisModel thesis;

    @ManyToMany(mappedBy = "chapters")
    private List<ChapterVersionModel> versions;

    public ChapterCoreDto toDto() {
        return ChapterCoreDto.builder()
                .id(this.id)
                .title(this.title)
                .titleEn(this.titleEn)
                .description(this.description)
                .descriptionEn(this.descriptionEn)
                .approvalStatus(this.approvalStatus)
                .userDataId(this.owners.stream().map(UserDataModel::getId).collect(Collectors.toList())) // later change name in dto and on frontend
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
