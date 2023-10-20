package com.binar.binarfud.repository;

import com.binar.binarfud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> getUserByUsername(String username);

    boolean existsByUsername(String username);

    @Modifying
    @Query("DELETE FROM User u WHERE u.username = :username")
    void deleteByUsername(String username);
}
