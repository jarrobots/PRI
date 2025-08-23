package wmi.amu.edu.pl.pri.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wmi.amu.edu.pl.pri.dto.ChapterCoreDto;
import wmi.amu.edu.pl.pri.models.ChapterModel;
import wmi.amu.edu.pl.pri.repositories.ChapterRepo;

@Service
public class ChapterService {

    ChapterRepo chapterRepo;

    public ChapterCoreDto findById(Long id) {
        ChapterModel chapter = chapterRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chapter not found with ID: " + id));
        return chapter.toDto();
    }

    public ChapterCoreDto update(Long id, ChapterCoreDto chapterDto) {
        ChapterModel chapter = chapterRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chapter not found with ID: " + id));

        chapter.applyDataFrom(chapterDto);
        ChapterModel savedChapter = chapterRepo.save(chapter);
        return savedChapter.toDto();
    }

    public ChapterCoreDto confirm(Long id) {
        ChapterModel chapter = chapterRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chapter not found with ID: " + id));
        chapter.setApprovalStatus("APPROVED");
        return chapterRepo.save(chapter).toDto();

    }
}
