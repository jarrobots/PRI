package wmi.amu.edu.pl.pri.models;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.core.env.Environment;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "thm_chapter_version")
public class ChapterVersionModel {

    @Transient  // Nie zapisuje w DB
    private transient Environment environment;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader", referencedColumnName = "id", nullable = false)
    private UserDataModel uploader;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "thm_chapter_chapter_version",
            joinColumns = @JoinColumn(name = "chapter_version_id"),
            inverseJoinColumns = @JoinColumn(name = "chapter_id")
    )
    private List<ChapterModel> chapters;

    @Column(columnDefinition = "text")
    private String link;

    private Long fileId;

    private String name;


    public UserDataModel getUploader(){
        if(uploader == null){
            uploader = new UserDataModel();
            uploader.setId((long) -1);
            uploader.setIndexNumber("");
            uploader.setEmail("");
            uploader.setFirstName("");
            uploader.setLastName("");
        }
        return uploader;

    }
}
