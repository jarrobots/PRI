package wmi.amu.edu.pl.pri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupsDto {
    private List<GroupDto> groups;
}
