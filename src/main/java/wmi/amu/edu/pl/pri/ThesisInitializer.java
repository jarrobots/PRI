package wmi.amu.edu.pl.pri;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import wmi.amu.edu.pl.pri.models.ChapterModel;
import wmi.amu.edu.pl.pri.models.ThesisModel;
import wmi.amu.edu.pl.pri.models.pri.ProjectModel;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;
import wmi.amu.edu.pl.pri.repositories.ChapterRepo;
import wmi.amu.edu.pl.pri.repositories.ProjectRepo;
import wmi.amu.edu.pl.pri.repositories.ThesisRepo;

@Component
@RequiredArgsConstructor
public class ThesisInitializer implements ApplicationRunner {

    private final ProjectRepo projectRepo;
    private final ThesisRepo thesisRepo;
    private final ChapterRepo chapterRepo;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        projectRepo.findAll().forEach(project -> {
            if (doesTheProjectHaveNotHaveItsThesisYet(project)) {
                ThesisModel thesis = new ThesisModel();
                thesis.setProject(project);
                var updatedThesis = thesisRepo.save(thesis);
                project.getStudents().forEach(student -> {
                    if (doesTheStudentHaveNoItsChapterYet(student)) {
                        ChapterModel chapter = new ChapterModel();
                        chapter.setOwner(student.getUserData());
                        chapter.setThesis(updatedThesis);
                        chapterRepo.save(chapter);
                    }
                });
            }
        });
    }

    private boolean doesTheStudentHaveNoItsChapterYet(StudentModel student) {
        return !chapterRepo.existsByOwnerId(student.getUserData().getId());
    }

    private boolean doesTheProjectHaveNotHaveItsThesisYet(ProjectModel project) {
        return !thesisRepo.existsByProjectId(project.getId());
    }
}