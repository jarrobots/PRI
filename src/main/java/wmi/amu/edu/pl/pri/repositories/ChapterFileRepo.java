package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wmi.amu.edu.pl.pri.models.ChapterFileModel;
import wmi.amu.edu.pl.pri.models.StudentModel;

import java.util.List;


public interface ChapterFileRepo extends JpaRepository<ChapterFileModel, Integer> {

    List<ChapterFileModel> findChapterFileModelByStudent(StudentModel student);
}
