package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;
import wmi.amu.edu.pl.pri.dto.FinalGradeDto;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Getter
@Setter
@Table(name = "thm_final_grade")
public class FinalGradeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToOne
    @JoinColumn(name = "chapter_id", referencedColumnName = "id", nullable = false)
    private ChapterModel chapter;

    public FinalGradeDto toDto() {
        return FinalGradeDto.builder()
                .id(this.getId())
                .text(this.getDescription())
                .chapterId(chapter.getId())
                .build();
    }
}
