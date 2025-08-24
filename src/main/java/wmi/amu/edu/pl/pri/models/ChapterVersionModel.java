package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
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
    private StudentModel owner;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", referencedColumnName = "id")
    private ChapterModel chapter;

    //plik na razie zostawiamy bez relacji zdefiniowanej przez Hibernate

    private Long fileId;

    private String name;

    public String getFormattedLink(String currentPort){
        return "http://localhost:%s/api/v1/download/".formatted(currentPort) + getFileId();
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
