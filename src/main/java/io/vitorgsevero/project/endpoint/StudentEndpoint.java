package io.vitorgsevero.project.endpoint;

import io.vitorgsevero.project.error.CustomErrorType;
import io.vitorgsevero.project.model.Student;
import io.vitorgsevero.project.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentEndpoint {

    private final StudentRepository studentDAO;

    public StudentEndpoint(StudentRepository studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GetMapping
    public ResponseEntity<?> listAll(){
        return new ResponseEntity<>(studentDAO.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id){
        Student student = studentDAO.findById(id).get();

        if(student == null){
            return new ResponseEntity<>(new CustomErrorType("student not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Student student){
        return new ResponseEntity<>(studentDAO.save(student), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        studentDAO.deleteById(id);
        return new ResponseEntity<>("Student was deleted", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student){
       studentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
