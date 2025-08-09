package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wmi.amu.edu.pl.pri.models.GroupModel;

import java.util.List;

public interface GroupRepo extends JpaRepository<GroupModel, Integer> {

    List<GroupModel> getAllBySupervisorId(Integer id);


    GroupModel findGroupModelById(Integer id);
}
