package wmi.amu.edu.pl.pri.dto.modeldto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StudentModelDto {
//this dto resembles formerly used StudentModel
    private Long id;
    private String fName;
    private String lName;
    private String email;
    private Long projectId;
}
