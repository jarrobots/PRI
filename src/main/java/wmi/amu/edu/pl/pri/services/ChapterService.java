package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.dto.ChapterDto;
import wmi.amu.edu.pl.pri.dto.ChecklistDto;
import wmi.amu.edu.pl.pri.models.ChecklistQuestionModel;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterService {
    VersionService versionService;
    ChecklistService checklistService;

    public ChapterDto getChapter(int studentId){
        return ChapterDto.builder()
                .checklist(checklistService.getChecklistByStudentId(studentId))
                .versions(versionService.getChapterVersionsByStudentId(studentId))
                .build();
    }
}
