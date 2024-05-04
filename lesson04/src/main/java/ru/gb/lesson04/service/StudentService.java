package ru.gb.lesson04.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.gb.lesson04.model.Student;
import ru.gb.lesson04.model.StudentInsertRequest;
import ru.gb.lesson04.model.StudentUpdateRequest;

import java.util.List;

@Service
@Log
public class StudentService {

    private final StudentDao studentDao;

    public StudentService(@Qualifier("jpa") StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public Student selectUserById(Long id) {
        return studentDao.selectStudentById(id).orElseThrow();
    }

    public void insertStudent(StudentInsertRequest insertRequest) {
        if (studentDao.existsStudentWithEmail(insertRequest.getEmail())){
            log.warning("email: %s already registered in system!".formatted(insertRequest.getEmail()));
            return;
        }
        studentDao.insertStudent(new Student(insertRequest.getName(), insertRequest.getEmail(), insertRequest.getAge()));
    }

    public List<Student> selectAllUsers() {
        return studentDao.selectAllStudents();
    }

    public void updateUser(StudentUpdateRequest updateRequest) {
        if (!studentDao.existsStudentWithId(updateRequest.getId())){
            log.warning("Update request rejected, user not found");
        }
        Student student = selectUserById(updateRequest.getId());

        student.setName(updateRequest.getName());
        student.setEmail(updateRequest.getEmail());
        student.setAge(updateRequest.getAge());
        studentDao.updateStudent(student);
    }

    public void deleteUserById(Long id) {
        if (!studentDao.existsStudentWithId(id)){
            log.warning("Update request rejected, user not found");
        }

        studentDao.deleteStudentById(id);
    }
}
