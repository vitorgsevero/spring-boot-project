package io.vitorgsevero.project.model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
public class Student extends AbstractEntity{

    @NotEmpty(message = "The field name is required")
    private String name;

    @Email(message = "Please enter a valid email address")
    @NotEmpty(message = "The field email is required")
    private String email;

    public Student(){}


    public Student(@NotEmpty(message = "The field name is required") String name, @Email(message = "Please enter a valid email address") @NotEmpty(message = "The field email is required") String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
