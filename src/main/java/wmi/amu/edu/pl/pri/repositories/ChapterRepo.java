package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wmi.amu.edu.pl.pri.models.ChapterModel;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

import java.util.List;

public interface ChapterRepo extends JpaRepository<ChapterModel, Long> {

    boolean existsByOwnersId(Long ownersId);


    @Query("SELECT c FROM ChapterModel c JOIN c.owners o WHERE o.id = :ownerId")
    List<ChapterModel> findByOwnerId(Long id);
}
