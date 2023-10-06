package com.binar.binarfud.service.impl;

import com.binar.binarfud.model.User;
import com.binar.binarfud.repository.UserRepository;
import com.binar.binarfud.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        try {
            log.info("trying to create user");
            Optional.ofNullable(user)
                    .map(newUser -> userRepository.save(user))
                    .map(Objects::nonNull)
                    .orElse(null);
            log.info("create user with username: {} successfully", user.getUsername());
            return user;
        } catch (Exception e) {
            e.getMessage();
            log.error("create user with username: {} failed", user.getUsername());
            throw e;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            log.info("trying to get user with username: {}", username);
            if (!userRepository.existsByUsername(username)) {
                throw new IllegalArgumentException("user with username " + username + " not found");
            }
            User user = userRepository.getUserByUsername(username).get();
            log.info("get user with username: {} successfully", username);
            return user;
        } catch (Exception e) {
            e.getMessage();
            log.error("get user with username: {} failed", username);
            throw e;
        }
    }

    @Override
    public User updateUserByUsername(String username, User user) {
        try {
            log.info("trying to update user with username: {}", username);
            if (!userRepository.existsByUsername(username)) {
                throw new IllegalArgumentException("user with username " + username + " not found");
            }
            User getUser = userRepository.getUserByUsername(username).get();
            user.setId(getUser.getId());
            log.info("update user with username: {} successfully", username);
            return userRepository.save(user);
        } catch (Exception e) {
            e.getMessage();
            log.error("update user with username: {} failed", username);
            throw e;
        }
    }

    @Override
    public List<User> getUsers() {
        try {
            log.info("trying to get all users");
            List<User> users = userRepository.findAll();
            if (users.isEmpty()) {
                throw new RuntimeException("user is empty");
            }
            log.info("get users successfully");
            return users;
        } catch (Exception e) {
            e.getMessage();
            log.error("get users failed");
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        try {
            log.info("trying to delete user with username: {}", username);
            if (!userRepository.existsByUsername(username)) {
                throw new IllegalArgumentException("user with username " + username + " not found");
            }
            userRepository.deleteByUsername(username);
            log.info("delete user with username: {} successfully", username);
        } catch (Exception e) {
            e.getMessage();
            log.error("delete user with username: {} failed", username);
            throw e;
        }
    }
}
