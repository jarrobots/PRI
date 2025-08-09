package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.dto.GroupDto;
import wmi.amu.edu.pl.pri.dto.GroupsDto;
import wmi.amu.edu.pl.pri.models.GroupModel;
import wmi.amu.edu.pl.pri.repositories.GroupRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {
    private final GroupRepo groupRepo;

    public GroupDto getGroupById(Integer id){
       GroupModel model = groupRepo.findGroupModelById(id);
       return mapToGroupDto(model);
    }
    public GroupsDto getAll(Integer id){
        List<GroupModel> models = groupRepo.getAllBySupervisorId(id);
        return mapToGroupsDto(models);
    }
    public GroupsDto findAll(){
        List<GroupModel> models = groupRepo.findAll();
        return mapToGroupsDto(models);
    }
    private GroupsDto mapToGroupsDto(List<GroupModel> groupModels){
        var dtos = groupModels.stream().map(groupModel -> {
            return GroupDto.builder()
                    .id(groupModel.getId())
                    .name(groupModel.getName())
                    .supervisor(groupModel.getSupervisor().toSupervisorModelDto())
                    .thesis(groupModel.getThesis())
                    .build();
        }).toList();
        return new GroupsDto(dtos);
    }

    private GroupDto mapToGroupDto(GroupModel groupModel){
        GroupDto dto = new GroupDto();
        dto.setId(groupModel.getId());
        dto.setName(groupModel.getName());
        dto.setSupervisor(groupModel.getSupervisor().toSupervisorModelDto());
        dto.setThesis(groupModel.getThesis());
        return dto;
    }
}
