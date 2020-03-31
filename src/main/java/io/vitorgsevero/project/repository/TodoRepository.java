package io.vitorgsevero.project.repository;

import io.vitorgsevero.project.model.Todo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TodoRepository extends PagingAndSortingRepository<Todo, Long> { //It extends the CrudRepository and add to new methods to findAll and paginate data

    List<Todo> findByNameIgnoreCaseContaining(String name);

}
