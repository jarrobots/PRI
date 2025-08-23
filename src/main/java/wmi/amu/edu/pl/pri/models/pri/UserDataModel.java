    package wmi.amu.edu.pl.pri.models.pri;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import wmi.amu.edu.pl.pri.dto.modeldto.UserDataDto;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Immutable
@Table(name = "user_data")
@Subselect("SELECT id, email, first_name, index_number, last_name FROM user_data")
public class UserDataModel {

    @Id
    private Long id;

    private String email;

    private String firstName;

    private String indexNumber;

    private String lastName;

    public UserDataDto toUserDataDto(){
        return UserDataDto.builder()
                .id(id)
                .fName(firstName)
                .lName(lastName).build();
    }

}
