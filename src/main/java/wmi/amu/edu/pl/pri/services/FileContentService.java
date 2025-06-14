package wmi.amu.edu.pl.pri.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.models.ChapterFileModel;
import wmi.amu.edu.pl.pri.models.FileContentModel;
import wmi.amu.edu.pl.pri.repositories.FileContentRepo;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileContentService {
    private final FileContentRepo repo;
    public Optional<FileContentModel> getFileById(Integer id){
        return repo.findById(id);
    }
    public Integer saveFile (byte[] data, String fileName, String fileType){
        FileContentModel model = new FileContentModel();
        model.setFileData(data);
        model.setFileName(fileName);
        model.setFileType(fileType);
        FileContentModel savedFile = repo.save(model);
        System.out.println("File with id:"+model.getId()+" saved successfully" );
        return savedFile.getId();
    }

}
