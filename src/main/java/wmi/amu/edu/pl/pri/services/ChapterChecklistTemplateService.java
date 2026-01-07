package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.models.ChapterChecklistTemplateModel;
import wmi.amu.edu.pl.pri.repositories.ChapterChecklistTemplateRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterChecklistTemplateService {
    private final ChapterChecklistTemplateRepo repo;
    private final ChapterService chapterService;

    public List<String> getChecklistTemplates(){
        List<ChapterChecklistTemplateModel> list = repo.findAll();
        return list.stream()
                .map(ChapterChecklistTemplateModel::getQuestion)
                .collect(Collectors.toList());
    }

    public void addChecklistTemplates(List<String> questions){
        repo.deleteAll();
        for(String item : questions){
            ChapterChecklistTemplateModel model = new ChapterChecklistTemplateModel();
            model.setQuestion(item);
           repo.save(model);
        }
    }
}
