package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wmi.amu.edu.pl.pri.models.StudentModel;

public interface StudentRepo extends JpaRepository<StudentModel, Integer> {
}
