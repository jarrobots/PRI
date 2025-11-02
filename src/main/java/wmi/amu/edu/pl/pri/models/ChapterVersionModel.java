package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader", referencedColumnName = "id")
    private UserDataModel uploader;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "chapter_version_chapter", // join table name (adjust as needed)
            joinColumns = @JoinColumn(name = "chapter_version_id"), // FK to this entity
            inverseJoinColumns = @JoinColumn(name = "chapter_id")   // FK to ChapterModel
    )
    private List<ChapterModel> chapters;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String link;
    //plik na razie zostawiamy bez relacji zdefiniowanej przez Hibernate

    private Long fileId;

    private String name;

    public String getFormattedLink(String port, String activeProfile){
        if (getLink() == null || getLink().equals("NO_LINK")){
            String baseUrl;
            if ("dev".equals(activeProfile)) {
                baseUrl = "http://150.254.78.134:%s/api/v1/download/";
            } else {
                baseUrl = "http://localhost:%s/api/v1/download/";
            }
            return baseUrl.formatted(port) + getFileId();
        }
        else {
            return getLink();
        }
    }

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
