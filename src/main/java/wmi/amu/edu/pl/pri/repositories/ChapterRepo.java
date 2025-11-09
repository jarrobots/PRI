package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.ChapterModel;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

import java.util.List;

public interface ChapterRepo extends JpaRepository<ChapterModel, Long> {

    boolean existsByOwnerId(Long ownersId);


    @Query("SELECT c FROM ChapterModel c WHERE c.id = :ownerId")
    ChapterModel findByOwnerId(@Param("ownerId") Long ownerId);
}
