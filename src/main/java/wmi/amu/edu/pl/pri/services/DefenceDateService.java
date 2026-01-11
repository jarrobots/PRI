package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.dto.TimelineDefenceDateDto;
import wmi.amu.edu.pl.pri.models.DefenceDateModel;
import wmi.amu.edu.pl.pri.repositories.DefenceDateRepo;

import java.util.Optional;

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

    public boolean saveDefenceDate(TimelineDefenceDateDto dto) {
        Optional<DefenceDateModel> existing = repo.findByChapterId(dto.getChapterId());
        DefenceDateModel model;
        model = existing.orElseGet(DefenceDateModel::new);
        model.setChapter(chapterService.getById(dto.getChapterId()));
        model.setDate(dto.getDate());
        model.setComment(dto.getComment());
        repo.save(model);
        return true;
    }

}
