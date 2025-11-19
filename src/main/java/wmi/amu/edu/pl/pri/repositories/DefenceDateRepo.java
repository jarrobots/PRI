package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.DefenceDateModel;

import java.util.Optional;

public interface DefenceDateRepo extends JpaRepository<DefenceDateModel,Long> {

    @Query("SELECT c FROM DefenceDateModel c WHERE c.chapter.id = :chapterId")
    public Optional<DefenceDateModel> findByChapterId(@Param("chapterId") Long id);
}
