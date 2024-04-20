package ru.gb;

import com.google.gson.Gson;

public class Program {
    public static void main(String[] args) {
        Student student = new Student("Alex", "Ivanov", 20);
        System.out.println(student);

        Gson gson = new Gson();
        String gsonString = gson.toJson(student);
        System.out.println(gsonString);

        Student student1 = gson.fromJson(gsonString, Student.class);
        System.out.println(student1);
    }
}
