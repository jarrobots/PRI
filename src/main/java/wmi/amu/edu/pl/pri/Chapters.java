package wmi.amu.edu.pl.pri;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "chapters")
public class Chapters {

    @Id
    private Integer id;
    private Integer groupId;
    private Integer chapterId;
    private String chapterTitle;

}
