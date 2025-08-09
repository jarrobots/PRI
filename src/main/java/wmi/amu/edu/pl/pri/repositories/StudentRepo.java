package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<StudentModel, Long> {

    @Query("SELECT s FROM StudentModel s WHERE s.userData.id = :userDataId")
    Optional<StudentModel> findByUserDataId(Long userDataId);

    @Query("SELECT c FROM StudentModel c WHERE c.project.id = :projectId")
    List<StudentModel> findStudentModelByProjectId(@Param("projectId") Long projectId);
}
