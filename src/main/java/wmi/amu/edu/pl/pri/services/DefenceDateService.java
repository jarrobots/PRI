package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.dto.TimelineDefenceDateDto;
import wmi.amu.edu.pl.pri.models.ChapterModel;
import wmi.amu.edu.pl.pri.models.DefenceDateModel;
import wmi.amu.edu.pl.pri.repositories.DefenceDateRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefenceDateService {

    private final DefenceDateRepo repo;
    private final ChapterService chapterService;

    public TimelineDefenceDateDto getDefenceDateByChapter(Long id) {
        var model = repo.findByChapterId(id);
        return model.map(DefenceDateModel::toDto).orElse(null);
    }

    public Long saveDefenceDate(TimelineDefenceDateDto dto) {
        ChapterModel chapter = chapterService.getById(dto.getChapterId());
        var model = repo.findByChapterId(chapter.getId()).orElse(new DefenceDateModel());

        model.setDate(dto.getDate());
        model.setComment(dto.getComment());
        model.setChapter(chapter);
        return repo.save(model).getId();
    }

}
