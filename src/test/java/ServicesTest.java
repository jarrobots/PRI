
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import wmi.amu.edu.pl.pri.dto.CommentDto;
import wmi.amu.edu.pl.pri.dto.ThesisCoreDto;

import wmi.amu.edu.pl.pri.models.ChapterVersionModel;
import wmi.amu.edu.pl.pri.models.ChecklistQuestionModel;
import wmi.amu.edu.pl.pri.models.pri.ProjectModel;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;
import wmi.amu.edu.pl.pri.models.pri.SupervisorModel;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;
import wmi.amu.edu.pl.pri.repositories.*;
import wmi.amu.edu.pl.pri.services.*;


import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
public class ServicesTest {

    private static UserDataRepo userDataRepo;
    private static StudentRepo studentRepo;
    private static SupervisorRepo supervisorRepo;
    private static ProjectRepo projectRepo;
    private static ThesisRepo thesisRepo;
    private static ChapterRepo chapterRepo;
    private static ChapterVersionRepo versionRepo;
    private static ChecklistRepo checklistRepo;
    private static CommentRepo commentRepo;
    private static FileContentRepo fileRepo;

    private static GroupService groupService;
    private static ThesisService thesisService;
    private static ChapterService chapterService;
    private static VersionService versionService;
    private static FileContentService fileService;
    private static CommentService commentService;
    private static ChecklistService checklistService;

    private static Long student1Id, student2Id, student3Id, supervisorId, s1Id, s2Id, s3Id, suId, proj, fileId, versionId, commentId, checklistId;

    @BeforeAll
    static void setUp() {
        var supervisor = new UserDataModel(null, "supervisor@pri.test", "Supervisor", "0000", "One");
        var student1 = new UserDataModel(null, "student1@pri.test", "Student", "1234", "One");
        var student2 = new UserDataModel(null, "student2@pri.test", "Student", "4321", "Two");
        var student3 = new UserDataModel(null, "student3@pri.test", "Student", "1111", "Three");

        supervisor = userDataRepo.save(supervisor);
        student1 = userDataRepo.save(student1);
        student2 = userDataRepo.save(student2);
        student3 = userDataRepo.save(student3);

        var su1 = new SupervisorModel(null, supervisor);
        var s1 = new StudentModel(null,"1234",null,student1);
        var s2 = new StudentModel(null,"4321",null,student2);
        var s3 = new StudentModel(null, "1111", null, student3);

        su1 = supervisorRepo.save(su1);
        s1 = studentRepo.save(s1);
        s2 = studentRepo.save(s2);
        s3  = studentRepo.save(s3);

        var proj1 = new ProjectModel(null, "test", "Project-TEST", su1, List.of(s1, s2, s3), null);
        proj1 = projectRepo.save(proj1);

        student1Id = student1.getId();
        student2Id = student2.getId();
        student3Id = student3.getId();
        supervisorId = supervisor.getId();

        s1Id = s1.getId();
        s2Id = s2.getId();
        s3Id = s3.getId();
        suId = su1.getId();

        proj = proj1.getId();
    }


    @Test
    void createAndConfirmThesisAndChaptersTest() {

        groupService.reloadGroups(supervisorId);
        var thesis = thesisService.findByProjectId(proj);
        assertNotNull("Thesis is null.", thesis);

        var coreDto = new ThesisCoreDto(thesis.getId(),"Thesis","Thesis","test","test","test");
        thesis = thesisService.update(thesis.getId(), coreDto);
        var chapters = thesis.getChapters();
        for( var chapter : chapters){

            chapter.setTitle("test-chapter");
            chapter.setTitleEn("test-chapter");
            chapter.setDescription("test-chapter");
            chapter.setDescriptionEn("test-chapter");
            chapter.setSupervisorComment("test-comment");
            chapterService.update(chapter.getId(), chapter);
            chapterService.confirm(chapter.getId());
        }
        assertNotNull("Chapters are null.", thesisService.getById(thesis.getId()).getChapters());

        for( var chapter : thesisService.getById(thesis.getId()).getChapters()){
            assertEquals("Chapters should be approved.", "APPRPVED", chapter.getApprovalStatus());
        }

        thesis = thesisService.confirm(thesis.getId());
        assertEquals("Thesis should be approved after confirm.", "APPROVED", thesis.getApprovalStatus());
    }

    @Test
    void addFileTest() throws IOException {
        ClassPathResource resource = new ClassPathResource("testFile.txt");
        MockMultipartFile file = new MockMultipartFile
                (
                        "file",
                        "testFile.txt",
                        "text/plain",
                        resource.getInputStream()
                );

        fileId = fileService.saveFile(file.getBytes(), file.getOriginalFilename(), file.getContentType());

        assertNotNull("File is null.", fileService.getFileById(fileId));

        var version = new ChapterVersionModel
                (
                        null,
                        userDataRepo.findById(student1Id).orElseThrow(),
                        new Date(),
                        List.of(chapterService.findChapterByOwnerId(student1Id)),
                        null,
                        fileId,
                        "Test"
                );
        versionId = versionService.saveFile(version);

        assertNotNull( "Version id is null.", versionService.getChapterVersionById(versionId));

    }

    @Test
    void addCommentTest(){
        var dto = new CommentDto(null,"Test-comment", supervisorId,"tescik", "testowy",null, versionId);
        commentService.addComment(dto);
        assertNotNull("Comment is null",commentService.getCommentByVersion(versionId));
    }

    @Test
    void addChecklistTest(){
        var checklistDto = checklistService.getChecklistByVersionId(versionId, student1Id);
        checklistDto.getModels().stream()
                .forEach(question -> question.setPassed(true));

        checklistService.setChecklist(checklistDto);
        checklistDto = checklistService.getChecklistByVersionId(versionId, student1Id);

        assertTrue("All questions should be passed.",
                checklistDto.getModels().stream().allMatch(ChecklistQuestionModel::isPassed));

    }


    @AfterAll
    static void cleanUp() {

        if (versionId != null) {
            long checklistId = checklistService.getChecklistByVersionId(versionId, student1Id).getId();
            long commentId = commentService.getCommentByVersion(versionId).getId();

            checklistRepo.deleteById(checklistId);
            commentRepo.deleteById(commentId);
            versionRepo.deleteById(versionId);
            fileRepo.deleteById(fileId);
        }

        if (proj != null) {
            var thesis = thesisService.findByProjectId(proj);
            if (thesis != null) {
                thesis.getChapters().forEach(chapter -> chapterRepo.deleteById(chapter.getId()));
                thesisRepo.deleteById(thesis.getId());
            }
        }

        projectRepo.deleteById(proj);
        supervisorRepo.deleteById(suId);
        studentRepo.deleteAllById(List.of(s1Id, s2Id, s3Id));
        userDataRepo.deleteAllById(List.of(student1Id, student2Id, student3Id, supervisorId));
    }
}
