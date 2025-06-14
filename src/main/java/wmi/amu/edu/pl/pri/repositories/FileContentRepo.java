package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wmi.amu.edu.pl.pri.models.FileContentModel;


public interface FileContentRepo extends JpaRepository<FileContentModel, Integer> {
}
