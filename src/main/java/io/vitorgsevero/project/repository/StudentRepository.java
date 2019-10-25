package io.vitorgsevero.project.repository;

import io.vitorgsevero.project.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> { //He extends the CrudRepository and add to new methods to findAll and paginate data

    List<Student> findByNameIgnoreCaseContaining(String name);

}
