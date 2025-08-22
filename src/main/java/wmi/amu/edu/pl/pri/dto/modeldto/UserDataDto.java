package wmi.amu.edu.pl.pri.dto.modeldto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDataDto {
    private Long id;
    private String fName;
    private String lName;
}
