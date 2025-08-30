package wmi.amu.edu.pl.pri.dto;

import lombok.*;
import wmi.amu.edu.pl.pri.dto.modeldto.StudentModelDto;
import wmi.amu.edu.pl.pri.dto.modeldto.SupervisorModelDto;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GroupDto {
    private Long id;
    private String name;
    private SupervisorModelDto supervisor;
    private List<StudentModelDto> students = new ArrayList<>();
    private Long thesisId;
    private Long projectId;
}
