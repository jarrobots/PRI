package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.models.ThesisChecklistTemplateModel;
import wmi.amu.edu.pl.pri.repositories.ThesisChecklistTemplateRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThesisChecklistTemplateService {
    private final ThesisChecklistTemplateRepo repo;
    private final UserDataService userDataService;

    public List<String> getChecklistTemplates(){
       List<ThesisChecklistTemplateModel> list = repo.findAll();
       return list.stream()
               .map(ThesisChecklistTemplateModel::getQuestion)
               .collect(Collectors.toList());
    }

    public void addChecklistTemplates(List<String> questions){;
        for(String item : questions){
            ThesisChecklistTemplateModel model  = new ThesisChecklistTemplateModel();
            model.setQuestion(item);
            repo.save(model);
        }
    }
}
