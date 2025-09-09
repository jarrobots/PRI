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

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VersionService {

    @Value("${server.port}")
    private String currentPort;

    @Value("${server.address:localhost}")
    private String currentAddress;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ChapterVersionRepo chapterVersionRepo;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private UserDataService userDataService;

    public ChapterVersionModel getChapterVersionById(Long id) {
        return chapterVersionRepo.getChapterVersionModelById(id);
    }

    public ChapterVersionsDto getChapterVersionsByOwnerId(Long studentId) {
        List<ChapterVersionModel> list = chapterVersionRepo.findByOwnerId(studentId);

        return mapToChapterVersionsDto(list);
    }

    private ChapterVersionsDto mapToChapterVersionsDto(List<ChapterVersionModel> chapterFileModels) {

        var dtos = chapterFileModels.stream()
                .map(chapterFileModel -> ChapterVersionDto.builder()
                        .id(chapterFileModel.getId())
                        .link(chapterFileModel.getFormattedLink(currentPort, currentAddress))
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

    public Long saveVersion(Long chapterId, AddVersionWithLinkCommandDto command) {

        ChapterModel chapter = chapterService.getById(chapterId);

        var chapterVersion = ChapterVersionModel.builder()
                .chapter(chapter)
                .owner(chapter.getOwner())
                .uploader(userDataService.getUserData(command.getUploaderUserDataId()))
                .date(new Date())
                .chapter(chapter)
                .link(command.getLink())
                .build();
        chapterVersionRepo.save(chapterVersion);

        System.out.println("Version of chapter with id:" + chapterId + " saved successfully");
        return chapterVersion.getId();
    }
}
