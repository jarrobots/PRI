package wmi.amu.edu.pl.pri.models.pri;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import wmi.amu.edu.pl.pri.dto.modeldto.StudentModelDto;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Immutable
@Table(name = "student")
@Subselect("SELECT id, pesel, study_year, project_id, user_data_id FROM student")
public class StudentModel {

    @Id
    private Long id;
    private String pesel;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectModel project;

    @OneToOne
    @JoinColumn(name = "user_data_id", nullable = false)
    private UserDataModel userData;

    public StudentModelDto toStudentModelDto() {
        return StudentModelDto.builder()
                .id(userData.getId()) //which id should be passed here
                .email(userData.getEmail())
                .fName(userData.getFirstName())
                .lName(userData.getLastName())
                .projectId(project.getId())
                .build();
    }
}
