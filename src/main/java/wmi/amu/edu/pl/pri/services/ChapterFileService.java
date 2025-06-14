package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.models.ChapterFileModel;
import wmi.amu.edu.pl.pri.models.StudentModel;
import wmi.amu.edu.pl.pri.repositories.ChapterFileRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterFileService {
    private final ChapterFileRepo repo;

    public List<ChapterFileModel> getAllFiles(){
        return repo.findAll();
    }
    public ChapterFileModel getFileById(Integer id){
        Optional<ChapterFileModel> optionalFile = repo.findById(id);
        if(optionalFile.isPresent()){
            return optionalFile.get();
        }
        System.out.println("File with id:"+id+"doesn't exist");
        return null;
    }
    public Integer saveFile (ChapterFileModel chapters){
        ChapterFileModel savedChapters = repo.save(chapters);

        System.out.println("File with id:"+chapters.getId()+" saved successfully" );
        return savedChapters.getId();
    }
    public ChapterFileModel updateFile (ChapterFileModel chapter) {
        Optional<ChapterFileModel> existingChapter = repo.findById(chapter.getId());

        ChapterFileModel updatedChapters = repo.save(chapter);

        System.out.println("File with id: "+chapter.getId()+" updated successfully");
        return updatedChapters;
    }
    public void deleteFileById (Integer id) {
        repo.deleteById(id);
    }
    public List<ChapterFileModel> findChapterFileModelByStudent(StudentModel student){
        return repo.findChapterFileModelByStudent(student);
    }
}
