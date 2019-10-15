package io.vitorgsevero.project.model;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;

public class Student {
    private int id;
    private String name;
    public static List<Student> studentList;

    static{
        studentRepository();
    }

    public Student(int id, String name) {
        this(name);
        this.id = id;
    }

    public Student(String name) {
        this.name = name;
    }

    public Student(){
    }

    private static void studentRepository(){
        studentList = new ArrayList<>(asList(new Student(1, "Vitor"), new Student(2, "Gustavo")));
    }

    private static void asList() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
