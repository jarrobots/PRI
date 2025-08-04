package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.GroupModel;
import wmi.amu.edu.pl.pri.models.StudentModel;

import java.util.List;

public interface StudentRepo extends JpaRepository<StudentModel, Integer> {

    @Query("SELECT c FROM StudentModel c WHERE c.group.id = :groupId")
    List<StudentModel> findStudentModelByGroupId(@Param("groupId") Integer groupId);
}
