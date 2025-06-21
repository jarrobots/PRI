package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.models.ChapterVersionModel;
import wmi.amu.edu.pl.pri.models.StudentModel;
import wmi.amu.edu.pl.pri.repositories.ChapterVersionRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterFileService {
    private final ChapterVersionRepo repo;

    public List<ChapterVersionModel> getAllFiles(){
        return repo.findAll();
    }
    public ChapterVersionModel getFileById(Integer id){
        Optional<ChapterVersionModel> optionalFile = repo.findById(id);
        if(optionalFile.isPresent()){
            return optionalFile.get();
        }
        System.out.println("File with id:"+id+"doesn't exist");
        return null;
    }
    public Integer saveFile (ChapterVersionModel chapters){
        ChapterVersionModel savedChapters = repo.save(chapters);

        System.out.println("File with id:"+chapters.getId()+" saved successfully" );
        return savedChapters.getId();
    }
    public ChapterVersionModel updateFile (ChapterVersionModel chapter) {
        Optional<ChapterVersionModel> existingChapter = repo.findById(chapter.getId());

        ChapterVersionModel updatedChapters = repo.save(chapter);

        System.out.println("File with id: "+chapter.getId()+" updated successfully");
        return updatedChapters;
    }
    public void deleteFileById (Integer id) {
        repo.deleteById(id);
    }
    public List<ChapterVersionModel> findChapterFileModelByStudent(StudentModel student){
        return repo.findChapterFileModelByStudent(student);
    }
}
