package wmi.amu.edu.pl.pri.dto.modeldto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDataDto {
    private Long id;
    private String fName;
    private String lName;
}
