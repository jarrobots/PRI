package wmi.amu.edu.pl.pri.models.pri;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

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

}