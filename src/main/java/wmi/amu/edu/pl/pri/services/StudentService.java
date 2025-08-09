package wmi.amu.edu.pl.pri.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wmi.amu.edu.pl.pri.dto.modeldto.StudentModelDto;
import wmi.amu.edu.pl.pri.models.pri.StudentModel;
import wmi.amu.edu.pl.pri.repositories.StudentRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepo repo;

    public List<StudentModelDto> findAllStudents() {
        return getAllStudents().stream().map(StudentModel::toStudentModelDto).toList();
    }

    public List<StudentModel> getAllStudents() {
        return repo.findAll();
    }

    public StudentModelDto findStudentById(Long id) {
        return getStudentById(id).toStudentModelDto();
    }

    public StudentModel getStudentById(Long id) {
        Optional<StudentModel> optionalFile = repo.findById(id);
        if (optionalFile.isPresent()) {
            return optionalFile.get();
        }
        System.out.println("Student with id:" + id + "doesn't exist");
        return null;
    }

    public StudentModelDto findStudentByUserDataId(Long id) {
        return getStudentById(id).toStudentModelDto();
    }

    public StudentModel getStudentByUserDataId(Long id) {
        Optional<StudentModel> optionalFile = repo.findByUserDataId(id);
        if (optionalFile.isPresent()) {
            return optionalFile.get();
        }
        System.out.println("Student with id:" + id + "doesn't exist");
        return null;
    }

    public List<StudentModelDto> findStudentsByProjectId(Long id) {
        return getStudentsByProjectId(id).stream().map(StudentModel::toStudentModelDto).toList();
    }

    public List<StudentModel> getStudentsByProjectId(Long id) {
        return repo.findStudentModelByProjectId(id);

    }

}