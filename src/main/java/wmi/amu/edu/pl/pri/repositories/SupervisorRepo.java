package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wmi.amu.edu.pl.pri.models.pri.SupervisorModel;

public interface SupervisorRepo extends JpaRepository<SupervisorModel, Long > {
}
