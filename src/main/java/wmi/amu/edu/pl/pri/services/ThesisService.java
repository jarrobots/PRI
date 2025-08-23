package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wmi.amu.edu.pl.pri.dto.ThesisCompleteDto;
import wmi.amu.edu.pl.pri.dto.ThesisCoreDto;
import wmi.amu.edu.pl.pri.models.ThesisModel;
import wmi.amu.edu.pl.pri.repositories.ThesisRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThesisService {

    private final ThesisRepo thesisRepo;

    public ThesisCompleteDto findById(Long id) {

        return thesisRepo.findById(id).map(ThesisModel::toCompleteDto).orElse(null);
    }

    public ThesisCompleteDto findByProjectId(Long id) {

        return thesisRepo.findByProjectId(id).map(ThesisModel::toCompleteDto).orElse(null);
    }

    public ThesisCompleteDto update(Long id, ThesisCoreDto dto) {

        Optional<ThesisModel> model = thesisRepo.findById(id);
        if (model.isEmpty())
            return null;
        else {
            model.ifPresent(m -> m.applyDataFrom(dto));
            return thesisRepo.save(model.get()).toCompleteDto();
        }
    }

    public ThesisCompleteDto confirm(Long id) {
        ThesisModel thesis = thesisRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Thesis not found with ID: " + id));
        thesis.setApprovalStatus("APPROVED");
        return thesisRepo.save(thesis).toCompleteDto();
    }
}
