package wmi.amu.edu.pl.pri.services;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import wmi.amu.edu.pl.pri.dto.GroupDto;
import wmi.amu.edu.pl.pri.dto.GroupsDto;
import wmi.amu.edu.pl.pri.models.ChapterModel;
import wmi.amu.edu.pl.pri.models.ThesisModel;
import wmi.amu.edu.pl.pri.models.pri.ProjectModel;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;
import wmi.amu.edu.pl.pri.models.pri.SupervisorModel;
import wmi.amu.edu.pl.pri.repositories.ChapterRepo;
import wmi.amu.edu.pl.pri.repositories.ProjectRepo;
import wmi.amu.edu.pl.pri.repositories.ThesisRepo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final ProjectRepo projectRepo;
    private final ThesisRepo thesisRepo;
    private final ChapterRepo chapterRepo;

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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void reloadGroups(Long supervisorUserDataId) {
        log.info("Attempt to generate theses and chapters");

        var thesesSupervisedByGivenUser = thesisRepo.findByProjectSupervisorUserDataId(supervisorUserDataId);

        thesisRepo.deleteAll(thesesSupervisedByGivenUser);

        for (ThesisModel thesis : thesesSupervisedByGivenUser) {
            ProjectModel project = thesis.getProject();
            if (project != null) {
                project.setThesis(null);
            }
        }

        projectRepo.findAll().forEach(project -> {

            if (doesTheProjectHaveNotHaveItsThesisYet(project)) {
                ThesisModel thesis = new ThesisModel();
                thesis.setProject(project);
                thesis.setApprovalStatus("PENDING");
                var updatedThesis = thesisRepo.save(thesis);
                project.getStudents().forEach(student -> {
                    if (doesTheStudentHaveNoItsChapterYet(student)) {
                        ChapterModel chapter = new ChapterModel();
                        chapter.setApprovalStatus("PENDING");
                        chapter.setOwners(Collections.singletonList(student.getUserData()));
                        chapter.setThesis(updatedThesis);
                        chapterRepo.save(chapter);
                    }
                });
            }
        });
    }

    private boolean doesTheStudentHaveNoItsChapterYet(StudentModel student) {
        return !chapterRepo.existsByOwnersId(student.getUserData().getId());
    }

    private boolean doesTheProjectHaveNotHaveItsThesisYet(ProjectModel project) {
        return !thesisRepo.existsByProjectId(project.getId());
    }

}
