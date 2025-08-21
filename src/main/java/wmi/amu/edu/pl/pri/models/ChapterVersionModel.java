package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;


import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
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

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Long fileId;

    private String name;
}
