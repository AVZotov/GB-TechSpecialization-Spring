package ru.gb.lesson04.model;

import lombok.Value;

@Value
public class StudentUpdateRequest{
    Long id;
    String name;
    String email;
    Integer age;
}
