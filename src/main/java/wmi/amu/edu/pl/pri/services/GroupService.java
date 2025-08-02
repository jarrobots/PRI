package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.models.GroupModel;
import wmi.amu.edu.pl.pri.repositories.GroupRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {
    private final GroupRepo groupRepo;

    public GroupModel getGroupById(Integer id){
       return groupRepo.findGroupModelByStudentsContainsAndId(id);
    }
}
