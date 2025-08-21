package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.dto.ChapterVersionDto;
import wmi.amu.edu.pl.pri.dto.ChapterVersionsDto;
import wmi.amu.edu.pl.pri.models.ChapterVersionModel;
import wmi.amu.edu.pl.pri.repositories.ChapterVersionRepo;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VersionService {

    @Value("${server.port}")
    private String currentPort;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ChapterVersionRepo chapterFileRepo;

    public ChapterVersionModel getChapterVersionById(Long id){
        return chapterFileRepo.findById(id).get();
    }

    public ChapterVersionsDto getChapterVersionsByOwnerId(Long studentId) {
        List<ChapterVersionModel> list = chapterFileRepo.findByOwnerId(studentId);

        return mapToChapterVersionsDto(list);
    }

    private ChapterVersionsDto mapToChapterVersionsDto(List<ChapterVersionModel> chapterFileModels){
        var dtos = chapterFileModels.stream()
                .map(chapterFileModel -> {
                    return ChapterVersionDto.builder()
                            .link(createLinkFrom(chapterFileModel))
                            .uploaderId(chapterFileModel.getUploader().getId())
                            .fileName(chapterFileModel.getName())
                            .uploadTime(chapterFileModel.getDate())
                            .build();
                })
                .sorted(Comparator.comparing(ChapterVersionDto::getUploadTime))
                .toList();
        return new ChapterVersionsDto(dtos);
    }

    public Long saveFile (ChapterVersionModel chapters){
        ChapterVersionModel savedChapters = chapterFileRepo.save(chapters);

        System.out.println("File with id:"+chapters.getId()+" saved successfully" );
        return savedChapters.getId();
    }

    private String createLinkFrom(ChapterVersionModel fileContent){
        return "http://localhost:%s/api/v1/download/".formatted(currentPort) + fileContent.getFileId();

    }
}
