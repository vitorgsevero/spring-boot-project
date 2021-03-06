package io.vitorgsevero.project.endpoint;

import io.swagger.annotations.*;
import io.vitorgsevero.project.error.ResourceNotFoundException;
import io.vitorgsevero.project.model.Student;
import io.vitorgsevero.project.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("v1")
public class StudentEndpoint {

    private final StudentRepository studentDAO;

    @Autowired
    public StudentEndpoint(StudentRepository studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GetMapping(path = "admin/students")
    @ApiOperation(value = "Return a list with all students", response = Student[].class)
    public ResponseEntity<?> listAll(Pageable pageable) {
        System.out.println(studentDAO.findAll());
        return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/findbyname/{name}")
    @ApiOperation(value = "Return a student by name", response = Student[].class)
    public ResponseEntity<?> findStudentByName(@PathVariable String name) {
        return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/{id}")
    @ApiOperation(value = "Return a student by id", response = Student[].class)
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id, Authentication authentication) {
        System.out.println(authentication);
        verifyIfStudentExists(id);
        Optional<Student> student = studentDAO.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping(path = "admin/students")
    @ApiOperation(value = "Save student", response = Student[].class)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody Student student) {
        return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "admin/students/{id}")
    @ApiOperation(value = "Delete student by id", response = Student[].class)
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfStudentExists(id);
        studentDAO.deleteById(id);
        return new ResponseEntity<>("Student was deleted", HttpStatus.OK);
    }

    @PutMapping(path = "admin/students")
    @ApiOperation(value = "Update student by id", response = Student[].class)
    public ResponseEntity<?> update(@RequestBody Student student) {
        verifyIfStudentExists(student.getId());
        studentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfStudentExists(Long id) {
        Optional<Student> student = studentDAO.findById(id);
        if (!student.isPresent()) {
            throw new ResourceNotFoundException("Student not found for ID: " + id);
        }
    }

}
