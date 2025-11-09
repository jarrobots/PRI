package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

import java.util.Optional;


public interface UserDataRepo extends JpaRepository<UserDataModel, Long> {

    @Query("SELECT u FROM UserDataModel u WHERE u.id = :id")
    Optional<UserDataModel> getUserDataModelById(@Param("id") Long id);
}
