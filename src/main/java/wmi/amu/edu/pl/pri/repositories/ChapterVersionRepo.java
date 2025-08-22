package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.ChapterVersionModel;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

import java.util.List;


public interface ChapterVersionRepo extends JpaRepository<ChapterVersionModel, Long> {
    List<ChapterVersionModel> findChapterFileModelByUploader(UserDataModel model);

    @Query("SELECT c FROM ChapterVersionModel c WHERE c.uploader.id = :uploaderId")
    List<ChapterVersionModel> findByOwnerId(@Param("uploaderId") Long uploaderId);

}