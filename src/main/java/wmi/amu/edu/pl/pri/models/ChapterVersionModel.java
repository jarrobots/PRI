package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;


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
    @JoinColumn(name = "student", referencedColumnName = "id", insertable=false, updatable=false)
    private StudentModel student;
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
}
