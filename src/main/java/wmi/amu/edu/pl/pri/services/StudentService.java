package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.dto.StudentDto;
import wmi.amu.edu.pl.pri.models.StudentModel;
import wmi.amu.edu.pl.pri.repositories.StudentRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepo repo;

    public List<StudentDto> getAllStudents(){
        return repo.findAll().stream().map(StudentDto::mapFromEntity).toList();
    }
    public StudentModel getStudentById(Integer id){
        Optional<StudentModel> optionalFile = repo.findById(id);
        if(optionalFile.isPresent()){
            return optionalFile.get();
        }
        System.out.println("Student with id:"+id+"doesn't exist");
        return null;
    }
    public StudentModel saveStudent (StudentModel student){
        StudentModel savedStudent = repo.save(student);

        System.out.println("Student with id:"+student.getId()+" saved successfully" );
        return savedStudent;
    }
    public StudentModel updateStudent (StudentModel student) {
        Optional<StudentModel> existingChapter = repo.findById(student.getId());

        StudentModel updatedStudent = repo.save(student);

        System.out.println("File with id: "+student.getId()+" updated successfully");
        return updatedStudent;
    }
    public void deleteStudentById (Integer id) {
        repo.deleteById(id);
    }
}