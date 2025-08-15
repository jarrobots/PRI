package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wmi.amu.edu.pl.pri.models.pri.ProjectModel;

import java.util.List;
import java.util.Optional;

public interface ProjectRepo extends JpaRepository<ProjectModel, Long> {

    List<ProjectModel> getAllBySupervisorId(Long id);


    Optional<ProjectModel> findById(Long id);
}
