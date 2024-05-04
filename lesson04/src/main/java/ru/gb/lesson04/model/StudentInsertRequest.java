package ru.gb.lesson04.model;

import lombok.Data;

@Data
public class StudentInsertRequest{
    String name;
    String email;
    Integer age;
}