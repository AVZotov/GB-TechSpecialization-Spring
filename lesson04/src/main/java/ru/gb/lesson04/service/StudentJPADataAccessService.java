package ru.gb.lesson04.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lesson04.model.Student;
import ru.gb.lesson04.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service("jpa")
@AllArgsConstructor
public class StudentJPADataAccessService implements StudentDao{

    private final StudentRepository studentRepository;

    @Override
    public List<Student> selectAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> selectStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public void insertStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public boolean existsStudentWithEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public boolean existsStudentWithId(Long studentId) {
        return studentRepository.existsById(studentId);
    }

    @Override
    public void deleteStudentById(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public void updateStudent(Student update) {

    }
}
