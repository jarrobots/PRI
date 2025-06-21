package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.models.ChecklistQuestionModel;
import wmi.amu.edu.pl.pri.repositories.ChecklistQuestionRepo;


import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChecklistQuestionService {
    private final ChecklistQuestionRepo repo;
    public Optional<ChecklistQuestionModel> getFileById(Integer id){
        return repo.findById(id);
    }
    public void saveQuestion(ChecklistQuestionModel model){
        repo.save(model);
    }
}
