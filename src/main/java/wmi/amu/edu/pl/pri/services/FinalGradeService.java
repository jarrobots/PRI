package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.models.FinalGradeModel;
import wmi.amu.edu.pl.pri.repositories.FinalGradeRepo;


@Service
@RequiredArgsConstructor
@Slf4j
public class FinalGradeService {
    private final FinalGradeRepo repo;

    public FinalGradeModel getModelByChapterId(Long chapterId){
        return repo.findByChapterId(chapterId).orElse(null);
    }
    public Long setFinalGrade(FinalGradeModel finalGradeModel){
        if(finalGradeModel.getId() == null || repo.findById(finalGradeModel.getId()).isEmpty()) {
            return repo.save(finalGradeModel).getId();
        }
        else{
            repo.deleteById(finalGradeModel.getId());
            finalGradeModel.setId(null);
            return repo.save(finalGradeModel).getId();
        }
    }
}
