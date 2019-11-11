package io.vitorgsevero.project.endpoint;

import io.vitorgsevero.project.error.ResourceNotFoundException;
import io.vitorgsevero.project.model.Student;
import io.vitorgsevero.project.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<?> listAll(Pageable pageable){
        System.out.println(studentDAO.findAll());
        return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id, Authentication authentication){
        System.out.println(authentication);
        verifyIfStudentExists(id);
        Optional<Student> student = studentDAO.findById(id);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/findbyname/{name}")
    public ResponseEntity<?> findStudentByName(@PathVariable String name){
        return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping(path = "admin/students")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody Student student){
        return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "admin/students/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        verifyIfStudentExists(id);
        studentDAO.deleteById(id);
        return new ResponseEntity<>("Student was deleted", HttpStatus.OK);
    }

    @PutMapping(path = "admin/students")
    public ResponseEntity<?> update(@RequestBody Student student){
       verifyIfStudentExists(student.getId());
       studentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfStudentExists(Long id){
        Optional<Student> student = studentDAO.findById(id);
        if(!student.isPresent()){
            throw new ResourceNotFoundException("Student not found for ID: " + id);
        }
    }

}
