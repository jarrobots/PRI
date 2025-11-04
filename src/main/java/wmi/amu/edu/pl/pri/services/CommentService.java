package wmi.amu.edu.pl.pri.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.dto.CommentDto;
import wmi.amu.edu.pl.pri.models.CommentModel;
import wmi.amu.edu.pl.pri.repositories.CommentRepo;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepo repo;
    private final UserDataService userService;
    private final VersionService versionService;

    public CommentDto getComment(Long id){
        CommentModel model = repo.findCommentById(id);
        return model.toCommentDto();

    }
    public Long addComment(CommentDto dto){
        CommentModel model = new CommentModel();
        model.setUploader(userService.getUserData(dto.getUploaderId()));
        model.setText(dto.getText());
        model.setVersionModel(versionService.getChapterVersionById(dto.getVersionId()));
        return repo.save(model).getId();
    }
}