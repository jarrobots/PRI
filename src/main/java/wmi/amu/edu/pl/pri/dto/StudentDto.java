package wmi.amu.edu.pl.pri.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wmi.amu.edu.pl.pri.models.GroupModel;
import wmi.amu.edu.pl.pri.models.StudentModel;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StudentDto {

    private Integer id;
    private String fName;
    private String lName;
    private String email;
    private Long groupID;

    public static StudentDto mapFromEntity(StudentModel entity) {
        return StudentDto.builder()
                .id(entity.getId())
                .fName(entity.getFName())
                .lName(entity.getLName())
                .email(entity.getEmail())
                .groupID(entity.getGroup().getId())
                .build();
    }
}
