package wmi.amu.edu.pl.pri.models.pri;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import wmi.amu.edu.pl.pri.dto.modeldto.SupervisorModelDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Immutable
@Subselect("SELECT id, user_data_id FROM supervisor")
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
                .id(userData.getId())
                .email(userData.getEmail())
                .fName(userData.getFirstName())
                .lName(userData.getLastName())
                .build();
    }
}