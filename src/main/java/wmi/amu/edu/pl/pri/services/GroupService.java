package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.dto.GroupDto;
import wmi.amu.edu.pl.pri.dto.GroupsDto;
import wmi.amu.edu.pl.pri.models.pri.ProjectModel;
import wmi.amu.edu.pl.pri.models.pri.SupervisorModel;
import wmi.amu.edu.pl.pri.repositories.ProjectRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final ProjectRepo projectRepo;

    public GroupDto getGroupById(Long id){
       ProjectModel model = projectRepo.findById(id).get();
       return mapToGroupDto(model);
    }

    public GroupsDto getGetGroupsBySupervisorId(Long id){
        List<ProjectModel> models = projectRepo.getAllBySupervisorId(id);
        return mapToGroupsDto(models);
    }
    public GroupsDto findAll(){
        List<ProjectModel> models = projectRepo.findAll();
        return mapToGroupsDto(models);
    }

    private GroupsDto mapToGroupsDto(List<ProjectModel> groupModels){
        var dtos = groupModels.stream()
                .map(ProjectModel::toGroupDto)
                .toList();
        return new GroupsDto(dtos);
    }
    private GroupDto mapToGroupDto(ProjectModel projectModel){
        GroupDto dto = new GroupDto();
        dto.setId(projectModel.getId()); //do usuniecia potem
        dto.setProjectId(projectModel.getId());
        dto.setName(projectModel.getName());
        dto.setSupervisor(Optional.ofNullable(projectModel.getSupervisor()).map(SupervisorModel::toSupervisorModelDto).orElse(null));
        dto.setThesisId(projectModel.getThesis()==null? null : projectModel.getThesis().getId());
        return dto;
    }

   /*private GroupDto mapProjectModelToGroupDto(ProjectModel projectModel){
        return GroupDto.builder()
                .projectId(projectModel.getId())
                .students(projectModel.getStudents().stream().map(StudentModel::toStudentModelDto).toList())
                .thesisId(projectModel.getThesis()==null ? null : projectModel.getThesis().getId())
                .supervisor(projectModel.getSupervisor().toSupervisorModelDto())
                .name(createGroupNameFromProjectName(projectModel.getName()))
                .build();
    }

    private String createGroupNameFromProjectName(String projectName){
        return "Grupa projektu \"" + projectName + "\"";
    }

    */
}
