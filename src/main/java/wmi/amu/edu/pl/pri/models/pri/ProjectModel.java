package wmi.amu.edu.pl.pri.models.pri;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import wmi.amu.edu.pl.pri.models.ThesisModel;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Immutable
@Table(name = "project")
@Subselect("SELECT id, description, name, supervisor_id FROM project")
public class ProjectModel {

    @Id
    private Long id;
    private String description;
    private String name;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private SupervisorModel supervisor;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<StudentModel> students;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "project_id")
    private ThesisModel thesis;


}