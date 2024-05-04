package ru.gb.lesson04.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.lesson04.model.Student;
import ru.gb.lesson04.model.StudentInsertRequest;
import ru.gb.lesson04.model.StudentUpdateRequest;
import ru.gb.lesson04.service.StudentService;

import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String selectAllStudents(Model model){
        List<Student> students = studentService.selectAllUsers();
        model.addAttribute("studentList", students);

        return "studentList-form";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        StudentInsertRequest insertRequest = new StudentInsertRequest();
        model.addAttribute("insertRequest", insertRequest);
        return "registration-form";
    }

    @PostMapping("/register")
    public String submitRegistrationForm(@ModelAttribute("insertRequest") StudentInsertRequest insertRequest){
        studentService.insertStudent(insertRequest);
        return "redirect:/students";
    }

    @GetMapping("/update/{id}")
    public String selectUserById(@PathVariable("id") Long id, Model model){
        Student student = studentService.selectUserById(id);
        StudentUpdateRequest updateRequest =
                new StudentUpdateRequest(id, student.getName(), student.getEmail(), student.getAge());

        model.addAttribute("updateStudent", updateRequest);
        return "update-form";
    }

    @PostMapping("/update")
    public String updateUser(StudentUpdateRequest updateRequest) {
        studentService.updateUser(updateRequest);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id){
        studentService.deleteUserById(id);

        return "redirect:/students";
    }
}
