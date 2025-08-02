package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wmi.amu.edu.pl.pri.models.SupervisorModel;

public interface SupervisorRepo extends JpaRepository<SupervisorModel, Integer> {
}
