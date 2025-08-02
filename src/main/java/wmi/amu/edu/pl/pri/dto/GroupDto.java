package wmi.amu.edu.pl.pri.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wmi.amu.edu.pl.pri.models.StudentModel;
import wmi.amu.edu.pl.pri.models.SupervisorModel;
import wmi.amu.edu.pl.pri.models.ThesisModel;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GroupDto {
    private Long id;
    private String name;
    private SupervisorModel supervisor;
    private List<StudentModel> students = new ArrayList<>();
    private ThesisModel thesis;
}
