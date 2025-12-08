package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.ChapterChecklistTemplateModel;
import wmi.amu.edu.pl.pri.models.UserChecklistTemplateModel;

import java.util.List;

public interface ChapterChecklistTemplateRepo extends JpaRepository<ChapterChecklistTemplateModel,Long> {
    @Query("SELECT c FROM ChapterChecklistTemplateModel c WHERE c.chapter.id = :chapterId")
    List<ChapterChecklistTemplateModel> findByChapterId(@Param("chapterId") Long id);
}
