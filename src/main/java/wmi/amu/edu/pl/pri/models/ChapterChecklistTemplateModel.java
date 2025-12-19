package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Getter
@Setter
@Table(name = "thm_chapter_checklist_templates")
public class ChapterChecklistTemplateModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chapter_id", referencedColumnName = "id", nullable = false)
    private ChapterModel chapter;

    @Column(name = "question")
    private String question;

}

