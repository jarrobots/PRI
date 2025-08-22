package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wmi.amu.edu.pl.pri.models.ChapterModel;

public interface ChapterRepo extends JpaRepository<ChapterModel, Long> {
}
