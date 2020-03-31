package io.vitorgsevero.project.repository;

import io.vitorgsevero.project.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository <User, Long> {
    User findByUsername(String username);
}
