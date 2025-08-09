package wmi.amu.edu.pl.pri.models.pri;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import wmi.amu.edu.pl.pri.dto.modeldto.SupervisorModelDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Immutable
@Subselect("SELECT id FROM supervisor")
@Table(name = "supervisor")
@Entity
public class SupervisorModel {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_data_id")
    private UserDataModel userData;

    public SupervisorModelDto toSupervisorModelDto(){
        return SupervisorModelDto.builder()
                .id(userData.getId()) //ktore id ma tu byc?
                .email(userData.getEmail())
                .fName(userData.getFirstName())
                .lName(userData.getLastName())
                .build();
    }
}