package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.models.StudentModel;
import wmi.amu.edu.pl.pri.models.SupervisorModel;
import wmi.amu.edu.pl.pri.repositories.StudentRepo;
import wmi.amu.edu.pl.pri.repositories.SupervisorRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupervisorService {

    private final SupervisorRepo repo;

    public List<SupervisorModel> getAllSupervisors(){
        return repo.findAll();
    }
    public SupervisorModel getSupervisorById(Integer id){
        Optional<SupervisorModel> optionalFile = repo.findById(id);
        if(optionalFile.isPresent()){
            return optionalFile.get();
        }
        System.out.println("Student with id:"+id+"doesn't exist");
        return null;
    }
}
