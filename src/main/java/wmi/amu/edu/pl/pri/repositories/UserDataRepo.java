package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

import java.util.Optional;


public interface UserDataRepo extends JpaRepository<UserDataModel, Long> {

    @Query("SELECT u FROM UserDataModel u WHERE u.id = :id")
    public Optional<UserDataModel> getUserDataModelById(Long id);
}
