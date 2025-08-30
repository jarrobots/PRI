package wmi.amu.edu.pl.pri.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GroupsDto {
    private List<GroupDto> dtos;
}
