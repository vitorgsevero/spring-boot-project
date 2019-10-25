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
