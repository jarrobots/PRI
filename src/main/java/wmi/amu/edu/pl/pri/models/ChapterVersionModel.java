package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;
import wmi.amu.edu.pl.pri.dto.ChapterVersionDto;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;


import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "thm_chapter_version")
public class ChapterVersionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader", referencedColumnName = "id", insertable=false, updatable=false)
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

    public Long getUploaderId(){
        if(uploader == null){
            return (long) -1;
        }
        else{
            return uploader.getId();
        }
    }
}
