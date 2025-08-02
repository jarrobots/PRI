package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wmi.amu.edu.pl.pri.models.GroupModel;
import wmi.amu.edu.pl.pri.models.StudentModel;

public interface StudentRepo extends JpaRepository<StudentModel, Integer> {

}
