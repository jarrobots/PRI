package wmi.amu.edu.pl.pri.models.pri;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.springframework.stereotype.Controller;
import wmi.amu.edu.pl.pri.dto.GroupDto;
import wmi.amu.edu.pl.pri.models.ThesisModel;

import java.util.List;

@Getter
@Setter
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

    @OneToOne(mappedBy = "project", fetch = FetchType.LAZY)
    @Setter
    private ThesisModel thesis;

    public GroupDto toGroupDto(){
        return GroupDto.builder()
                .projectId(id)
                .students(students.stream().map(StudentModel::toStudentModelDto).toList())
                .thesisId(thesis==null ? null : thesis.getId())
                .supervisor(supervisor.toSupervisorModelDto())
                .name(String.format("Grupa projektu \"%s\"",name))
                .build();
    }
}