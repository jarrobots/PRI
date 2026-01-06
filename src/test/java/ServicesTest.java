
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import wmi.amu.edu.pl.pri.dto.ThesisCoreDto;

import wmi.amu.edu.pl.pri.models.pri.ProjectModel;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;
import wmi.amu.edu.pl.pri.models.pri.SupervisorModel;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;
import wmi.amu.edu.pl.pri.repositories.*;
import wmi.amu.edu.pl.pri.services.GroupService;


import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ServicesTest {

    private static UserDataRepo userDataRepo;
    private static StudentRepo studentRepo;
    private static SupervisorRepo supervisorRepo;
    private static ProjectRepo projectRepo;
    private static ThesisRepo thesisRepo;

    private static GroupService groupService;

    private static UserDataModel student1;
    private static UserDataModel student2;
    private static UserDataModel student3;
    private static UserDataModel supervisor;

    @BeforeAll
    static void setUp() {
        // Supervisor
        supervisor = new UserDataModel(null, "supervisor@pri.test", "Supervisor", "0000", "One");
        student1 = new UserDataModel(null, "student1@pri.test", "Student", "1234", "One");
        student2 = new UserDataModel(null, "student2@pri.test", "Student", "4321", "Two");
        student3 = new UserDataModel(null, "student3@pri.test", "Student", "1111", "Three");

        supervisor.setId(userDataRepo.save(supervisor).getId());
        student1.setId(userDataRepo.save(student1).getId());
        student2.setId(userDataRepo.save(student2).getId());
        student3.setId(userDataRepo.save(student3).getId());

        var su1 = new SupervisorModel(null, supervisor);
        var s1 = new StudentModel(null,"1234",null,student1);
        var s2 = new StudentModel(null,"4321",null,student2);
        var s3 = new StudentModel(null, "1111", null, student3);

        supervisorRepo.save(su1);
        studentRepo.save(s1);
        studentRepo.save(s2);
        studentRepo.save(s3);

        var proj1 = new ProjectModel(null, "test", "Project-TEST", su1, List.of(s1, s2, s3), null);
        projectRepo.save(proj1);
    }


    @Test
    void createAndConfirmThesisTest() {

        var dto1 = new ThesisCoreDto(null,"Thesis","Thesis","test","test","test");
        var dto2 = new ThesisCoreDto(null,"Thesis","Thesis","test","test","test");
        var dto3 = new ThesisCoreDto(null,"Thesis","Thesis","test","test","test");


    }

}
