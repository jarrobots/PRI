package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wmi.amu.edu.pl.pri.models.ChapterModel;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

public interface ChapterRepo extends JpaRepository<ChapterModel, Long> {

    boolean existsByOwnerId(Long id);

    ChapterModel findByOwner(UserDataModel ownerUserData);
    ChapterModel findByOwnerId(Long id);
}
