package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

import java.util.Date;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private UserDataModel owner;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", referencedColumnName = "id")
    private ChapterModel chapter;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String link;
    //plik na razie zostawiamy bez relacji zdefiniowanej przez Hibernate

    private Long fileId;

    private String name;

    public String getFormattedLink(String port, String address){
        if (getLink() == null || getLink().equals("NO_LINK"))
            return "http://%s:%s/api/v1/download/".formatted(address,port) + getFileId();
        else
            return getLink();
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
