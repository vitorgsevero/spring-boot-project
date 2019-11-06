package io.vitorgsevero.project;

import io.vitorgsevero.project.model.Student;
import io.vitorgsevero.project.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
        student.setName("Vitor777");
        student.setEmail("vitorgsevero111@gmail.com");
        this.studentRepository.save(student);
        this.studentRepository.findById(student.getId());
        Assertions.assertThat(student.getName()).isEqualTo("Vitor777");
        Assertions.assertThat(student.getEmail()).isEqualTo("vitorgsevero111@gmail.com");

    }

    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase (){
        Student student = new Student("vitor1", "vitorgsevero@gmail.com");
        Student student2 = new Student("Vitor1", "vitorgsevero2@gmail.com");
        //Student studentA = new Student("Vitor1", "vitorgsevero2@gmail.com");
        this.studentRepository.save(student);
        this.studentRepository.save(student2);
       // this.studentRepository.save(studentA);
        List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("vitor");
        Assertions.assertThat(studentList.size()).isEqualTo(2);

    }

    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException (){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("The field name is required");

        this.studentRepository.save(new Student());

    }

    @Test
    public void createWhenEmailIsNullShouldThrowConstraintViolationException (){
        thrown.expect(ConstraintViolationException.class);
        Student student = new Student();
        student.setName("Vitor Teste");
        student.setEmail("vitorgsevero@gmail.com");
        this.studentRepository.save(student);
    }

    @Test
    public void createWhenEmailIsNotValidShouldThrowConstraintViolationException (){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Please enter a valid email address");

        Student student = new Student();
        student.setName("Vitor Teste");
        student.setEmail("vitorgsevero");
        this.studentRepository.save(student);
    }



}
