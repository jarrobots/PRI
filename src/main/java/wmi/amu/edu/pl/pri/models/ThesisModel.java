package wmi.amu.edu.pl.pri.models;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

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
    private List<ChapterModel> chapters;

    @OneToOne(mappedBy = "thesis")
    private GroupModel group;
}

