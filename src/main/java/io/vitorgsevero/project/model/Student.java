package io.vitorgsevero.project.model;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;

@Entity
public class Student extends AbstractEntity{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
