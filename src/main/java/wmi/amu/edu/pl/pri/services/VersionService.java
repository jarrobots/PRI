package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.dto.ChapterVersionDto;
import wmi.amu.edu.pl.pri.dto.ChapterVersionsDto;
import wmi.amu.edu.pl.pri.models.ChapterFileModel;
import wmi.amu.edu.pl.pri.models.StudentModel;
import wmi.amu.edu.pl.pri.repositories.ChapterFileRepo;

import java.util.Comparator;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class VersionService {

    @Value("${server.port}")
    private String currentPort;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ChapterFileRepo chapterFileRepo;

    public ChapterVersionsDto getChapterVersionsByStudentId(Integer studentId) {
        List<ChapterFileModel> list = chapterFileRepo.findByStudentId(studentId);

        return mapToChapterVersionsDto(list);
    }

    private ChapterVersionsDto mapToChapterVersionsDto(List<ChapterFileModel> chapterFileModels){
        var dtos = chapterFileModels.stream()
                .map(chapterFileModel -> {
                    return ChapterVersionDto.builder()
                            .link(createLinkFrom(chapterFileModel))
                            .uploaderId(chapterFileModel.getStudent().getId())
                            .uploadTime(chapterFileModel.getDate())
                            .build();
                })
                .sorted(Comparator.comparing(ChapterVersionDto::getUploadTime))
                .toList();
        return new ChapterVersionsDto(dtos);
    }

    private String createLinkFrom(ChapterFileModel fileContent){
        return "http://localhost:%s/api/v1/download/".formatted(currentPort) + fileContent.getFileId();

    }
}
