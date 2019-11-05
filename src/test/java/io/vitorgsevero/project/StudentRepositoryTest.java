package io.vitorgsevero.project;

import io.vitorgsevero.project.model.Student;
import io.vitorgsevero.project.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentRepositoryTest  {
    @Autowired
    private StudentRepository studentRepository;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData(){
        Student student = new Student("vitor1", "vitorgsevero@gmail.com");
        this.studentRepository.save(student);
        Assertions.assertThat(student.getId()).isNotNull();
        Assertions.assertThat(student.getName()).isEqualTo("vitor1");
        Assertions.assertThat(student.getEmail()).isEqualTo("vitorgsevero@gmail.com");
    }

    @Test
    public void deleteShouldRemoveData(){
        Student student = new Student("vitor1", "vitorgsevero@gmail.com");
        this.studentRepository.save(student);
        studentRepository.delete(student);
        Assertions.assertThat(studentRepository.findById(student.getId())).isNull();
    }

    @Test
    public void updateShouldChangeAndPersistData(){
        Student student = new Student("vitor1", "vitorgsevero@gmail.com");
        this.studentRepository.save(student);
        student.setName("vitor2");
        student.setEmail("vitorgsevero2@gmail.com");
        this.studentRepository.save(student);
        this.studentRepository.findById(student.getId());
        Assertions.assertThat(student.getName()).isEqualTo("vitor2");
        Assertions.assertThat(student.getEmail()).isEqualTo("vitorgsevero2@gmail.com");
    }
}
