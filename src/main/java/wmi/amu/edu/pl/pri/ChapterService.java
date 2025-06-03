package wmi.amu.edu.pl.pri;

import wmi.amu.edu.pl.pri.Chapters;
import wmi.amu.edu.pl.pri.ChapterRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterService {
    private final ChapterRepo chapterRepo;

    public List<Chapters> getAllChapters(){
        return chapterRepo.findAll();
    }
    public Chapters getChapterById(Integer id){
        Optional<Chapters> optionalEmployee = chapterRepo.findById(id);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        System.out.println("Employee with id:"+id+"doesn't exist");
        return null;
    }
    public Chapters saveChapter (Chapters chapters){
        Chapters savedChapters = chapterRepo.save(chapters);

        System.out.println("Employee with id:"+chapters.getId()+" saved successfully" );
        return savedChapters;
    }
    public Chapters updateChapters (Chapters chapters) {
        Optional<Chapters> existingEmployee = chapterRepo.findById(chapters.getId());

        Chapters updatedChapters = chapterRepo.save(chapters);

        log.info("Employee with id: "+chapters.getId()+" updated successfully");
        return updatedChapters;
    }
    public void deleteChaptersById (Integer id) {
        chapterRepo.deleteById(id);
    }
}
