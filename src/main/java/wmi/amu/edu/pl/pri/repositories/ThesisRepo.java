package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wmi.amu.edu.pl.pri.models.ThesisModel;

import java.util.Optional;

public interface ThesisRepo extends JpaRepository<ThesisModel, Long> {


    Optional<ThesisModel> findById(Long aLong);
}
