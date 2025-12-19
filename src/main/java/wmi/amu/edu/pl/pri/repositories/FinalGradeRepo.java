package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.FinalGradeModel;

import java.util.Optional;

public interface FinalGradeRepo extends JpaRepository<FinalGradeModel,Long> {

    @Query("SELECT c FROM FinalGradeModel c WHERE c.chapter.id = :chapterId")
    public Optional<FinalGradeModel> findByChapterId(@Param("chapterId") Long id);
}
