package io.vitorgsevero.project.endpoint;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.vitorgsevero.project.model.Todo;
import io.vitorgsevero.project.repository.TodoRepository;


@RestController
@RequestMapping("v1")
public class TodoEndpoint {

    private final TodoRepository todo;

    @Autowired
    public TodoEndpoint(TodoRepository todo) {
        this.todo = todo;
    }

    @GetMapping(path = "/todo")
    @ApiOperation(value = "Return a list with all to do data", response = Todo[].class)
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(todo.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/todo/{id}")
    @ApiOperation(value = "Return a todo by id", response = Todo[].class)
    public ResponseEntity<?> getToDoById(@PathVariable("id") Long id) {
        Optional<Todo> todo = todo.findById(id);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PostMapping(path = "/todo")
    @ApiOperation(value = "Save student", response = Todo[].class)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody Todo todo) {
        return new ResponseEntity<>(todo.save(todo), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "todo/{id}")
    @ApiOperation(value = "Delete todo by id", response = Todo[].class)
    public ResponseEntity<?> delete(@PathVariable Long id) {
    	todo.deleteById(id);
        return new ResponseEntity<>("todo was deleted", HttpStatus.OK);
    }

    @PutMapping(path = "/todo")
    @ApiOperation(value = "Update todo by id", response = Todo[].class)
    public ResponseEntity<?> update(@RequestBody Todo todo) {
    	todo.save(todo);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
