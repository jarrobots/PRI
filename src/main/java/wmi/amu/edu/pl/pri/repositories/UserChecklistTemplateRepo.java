package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.UserChecklistTemplateModel;

import java.util.List;

public interface UserChecklistTemplateRepo extends JpaRepository<UserChecklistTemplateModel,Long> {

    @Query("SELECT c FROM UserChecklistTemplateModel c WHERE c.user.id = :ownerId")
    List<UserChecklistTemplateModel> findByUserId(@Param("ownerId") Long id);
}
