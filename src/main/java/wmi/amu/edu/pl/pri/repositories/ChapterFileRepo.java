package wmi.amu.edu.pl.pri.repositories;

import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.ChapterFileModel;
import wmi.amu.edu.pl.pri.models.StudentModel;

import java.util.List;


public interface ChapterFileRepo extends JpaRepository<ChapterFileModel, Integer> {
    List<ChapterFileModel> findChapterFileModelByStudent(StudentModel student);

    @Query("SELECT c FROM ChapterFileModel c WHERE c.student.id = :studentId")
    List<ChapterFileModel> findByStudentId(@Param("studentId") Integer studentId);

}