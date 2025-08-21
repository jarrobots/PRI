package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.ChecklistModel;

import java.util.Optional;


public interface ChecklistRepo extends JpaRepository<ChecklistModel,Long> {


    @Query("SELECT c FROM ChecklistModel c WHERE c.versionModel.id = :versionId")
    Optional<ChecklistModel> findByVersionId(@Param("versionId") Long id);

}

