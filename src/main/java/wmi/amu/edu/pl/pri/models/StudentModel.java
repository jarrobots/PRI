package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "student")
public class StudentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String fName;
    private String lName;
    private String email;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupModel group;
}
