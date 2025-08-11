package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;


import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "thm_chapter_file")
public class ChapterVersionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student", referencedColumnName = "id", insertable=false, updatable=false)
    private StudentModel student;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private StudentModel owner;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private Integer fileId;
    private String name;
}
