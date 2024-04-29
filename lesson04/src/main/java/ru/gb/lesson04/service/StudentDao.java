package ru.gb.lesson04.service;

import ru.gb.lesson04.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    List<Student> selectAllStudents();
    Optional<Student> selectStudentById(Long studentId);
    void insertStudent(Student student);
    boolean existsStudentWithEmail(String email);
    boolean existsStudentWithId(Long studentId);
    void deleteStudentById(Long studentId);
    void updateStudent(Student update);
}
