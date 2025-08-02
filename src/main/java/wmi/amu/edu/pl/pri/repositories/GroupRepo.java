package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wmi.amu.edu.pl.pri.models.GroupModel;

public interface GroupRepo extends JpaRepository<GroupModel, Integer> {
    GroupModel findGroupModelByStudentsContainsAndId(Integer id);
}
