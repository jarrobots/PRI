package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.models.UserChecklistTemplateModel;
import wmi.amu.edu.pl.pri.repositories.UserChecklistTemplateRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserChecklistTemplateService {
    private final UserChecklistTemplateRepo repo;
    private final UserDataService userDataService;

    public List<String> getChecklistTemplates(Long userId){
       List<UserChecklistTemplateModel> list = repo.findByUserId(userId);
       return list.stream()
               .map(UserChecklistTemplateModel::getQuestion)
               .collect(Collectors.toList());
    }

    public void addChecklistTemplates(Long userId, List<String> questions){
        var user = userDataService.getUserData(userId);
        for(String item : questions){
            UserChecklistTemplateModel model  = new UserChecklistTemplateModel();
            model.setQuestion(item);
            model.setUser(user);
            repo.save(model);
        }
    }
}
