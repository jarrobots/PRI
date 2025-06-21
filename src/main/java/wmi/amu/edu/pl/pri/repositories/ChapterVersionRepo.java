package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.ChapterVersionModel;
import wmi.amu.edu.pl.pri.models.StudentModel;

import java.util.List;


public interface ChapterVersionRepo extends JpaRepository<ChapterVersionModel, Integer> {
    List<ChapterVersionModel> findChapterFileModelByStudent(StudentModel student);

    @Query("SELECT c FROM ChapterVersionModel c WHERE c.student.id = :studentId")
    List<ChapterVersionModel> findByStudentId(@Param("studentId") Integer studentId);

}