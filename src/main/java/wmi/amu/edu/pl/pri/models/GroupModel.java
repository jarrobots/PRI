package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "group_table")
public class GroupModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thesis_id", unique = true)
    private ThesisModel thesis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor", referencedColumnName = "id")
    private SupervisorModel supervisor;
}
