package com.binar.binarfud.repository;

import com.binar.binarfud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> getUserByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);
}
