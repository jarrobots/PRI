package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.dto.AddVersionWithLinkCommandDto;
import wmi.amu.edu.pl.pri.dto.ChapterVersionDto;
import wmi.amu.edu.pl.pri.dto.ChapterVersionsDto;
import wmi.amu.edu.pl.pri.models.ChapterModel;
import wmi.amu.edu.pl.pri.models.ChapterVersionModel;
import wmi.amu.edu.pl.pri.repositories.ChapterVersionRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VersionService {

    @Value("${server.port}")
    private String currentPort;

    @Value("${spring.profiles.active:}")
    private String activeProfile;


    @Autowired
    private ChapterVersionRepo chapterVersionRepo;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private UserDataService userDataService;

    public ChapterVersionModel getChapterVersionById(Long id) {
        return chapterVersionRepo.getChapterVersionModelById(id);
    }

    public ChapterVersionsDto getChapterVersionByChapterId(Long chapterId) {
        return mapToChapterVersionsDto(chapterVersionRepo.getChapterVersionModelsByChapterId(chapterId));
    }

    private ChapterVersionsDto mapToChapterVersionsDto(List<ChapterVersionModel> chapterFileModels) {

        var dtos = chapterFileModels.stream()
                .map(chapterFileModel -> ChapterVersionDto.builder()
                        .id(chapterFileModel.getId())
                        .link(chapterFileModel.getFormattedLink(currentPort, activeProfile))
                        .uploaderId(chapterFileModel.getUploader().getId())
                        .uploaderFName(chapterFileModel.getUploader().getFirstName())
                        .uploaderLName(chapterFileModel.getUploader().getLastName())
                        .fileName(chapterFileModel.getName())
                        .uploadTime(chapterFileModel.getDate())
                        .build()
                )
                .sorted(Comparator.comparing(ChapterVersionDto::getUploadTime))
                .toList();
        return new ChapterVersionsDto(dtos);
    }

    public Long saveFile(ChapterVersionModel chapters) {
        ChapterVersionModel savedChapters = chapterVersionRepo.save(chapters);

        System.out.println("File with id:" + chapters.getId() + " saved successfully");
        return savedChapters.getId();
    }

    public Long saveVersion(List<Long> chapterId, AddVersionWithLinkCommandDto command) {

        List<ChapterModel> chapters = chapterId.stream()
                .map(chapterService::getById)
                .collect(Collectors.toList());


        var chapterVersion = ChapterVersionModel.builder()
                .chapters(chapters)
                .uploader(userDataService.getUserData(command.getUploaderUserDataId()))
                .date(new Date())
                .link(command.getLink())
                .build();
        chapterVersionRepo.save(chapterVersion);

        System.out.println("Version of chapter with id:" + chapterId + " saved successfully");
        return chapterVersion.getId();
    }
}
