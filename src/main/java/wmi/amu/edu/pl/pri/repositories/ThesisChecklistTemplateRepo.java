package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.ThesisChecklistTemplateModel;

import java.util.List;

public interface ThesisChecklistTemplateRepo extends JpaRepository<ThesisChecklistTemplateModel,Long> {

}
