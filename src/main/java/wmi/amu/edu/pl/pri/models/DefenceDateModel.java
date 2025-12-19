package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;
import wmi.amu.edu.pl.pri.dto.TimelineDefenceDateDto;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Getter
@Setter
@Table(name = "thm_defence_date")
public class DefenceDateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "chapter_id", referencedColumnName = "id", nullable = false)
    private ChapterModel chapter;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "defence_date")
    private Date date;

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    public TimelineDefenceDateDto toDto() {
        return TimelineDefenceDateDto.builder()
                .chapterId(this.getChapter().getId())
                .date(this.getDate())
                .comment(this.getComment())
                .build();
    }
}
