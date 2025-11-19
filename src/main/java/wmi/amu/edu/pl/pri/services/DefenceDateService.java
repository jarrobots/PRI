package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.dto.DefenseDateDto;
import wmi.amu.edu.pl.pri.models.DefenceDateModel;
import wmi.amu.edu.pl.pri.repositories.DefenceDateRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefenceDateService {

    private final DefenceDateRepo repo;
    private final ChapterService chapterService;

    public DefenseDateDto getDefenceDateByChapter(Long id) {
        var model = repo.findByChapterId(id);
        return model.map(DefenceDateModel::toDto).orElse(null);
    }

    public Long saveDefenceDate(DefenseDateDto dto) {
        var model = new DefenceDateModel();
        model.setDate(dto.getDate());
        model.setComment(dto.getComment());
        model.setChapter(chapterService.getById(dto.getChapterId()));
        return repo.save(model).getId();
    }

}
