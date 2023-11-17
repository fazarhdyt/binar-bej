package com.binar.binarfud.service.impl;

import com.binar.binarfud.dto.UserDto;
import com.binar.binarfud.exception.ResourceNotFoundException;
import com.binar.binarfud.model.User;
import com.binar.binarfud.repository.UserRepository;
import com.binar.binarfud.service.EntityMapper;
import com.binar.binarfud.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDto createUser(User user) {
        try {
            log.info("trying to create user");
            Optional.ofNullable(user)
                    .map(newUser -> userRepository.save(user))
                    .map(Objects::nonNull)
                    .orElse(null);
            log.info("create user with username: {} successfully", user.getUsername());
            return EntityMapper.userToUserDto(user);
        } catch (Exception e) {
            log.error("create user with username: {} failed\n", user.getUsername() + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        try {
            log.info("trying to get user with username: {}", username);
            if (!userRepository.existsByUsername(username)) {
                throw new ResourceNotFoundException("user", "username", username);
            }
            User user = userRepository.getUserByUsername(username).get();
            log.info("get user with username: {} successfully", username);
            return EntityMapper.userToUserDto(user);
        } catch (Exception e) {
            log.error("get user with username: {} failed\n", username + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public UserDto updateUserByUsername(String username, User user) {
        try {
            log.info("trying to update user with username: {}", username);
            if (!userRepository.existsByUsername(username)) {
                throw new ResourceNotFoundException("user", "username", username);
            }
            User updateUser = userRepository.getUserByUsername(username).get();
            updateUser.setId(updateUser.getId());
            updateUser.setUsername(user.getUsername() == null ? updateUser.getUsername() : user.getUsername());
            updateUser.setEmail(user.getEmail() == null ? updateUser.getEmail() : user.getEmail());
            updateUser.setPassword(user.getPassword() == null ? updateUser.getPassword() : user.getPassword());
            updateUser.setRoles(user.getRoles() == null ? updateUser.getRoles() : user.getRoles());
            log.info("update user with username: {} successfully", username);
            return EntityMapper.userToUserDto(userRepository.save(updateUser));
        } catch (Exception e) {
            log.error("update user with username: {} failed\n", username + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
        try {
            log.info("trying to get all users");
            List<User> users = userRepository.findAll();
            if (users.isEmpty()) {
                throw new RuntimeException("user is empty");
            }
            List<UserDto> usersDto = new ArrayList<>();
            for (User user : users) {
                UserDto userDto = EntityMapper.userToUserDto(user);
                usersDto.add(userDto);
            }
            log.info("get users successfully");
            return usersDto;
        } catch (Exception e) {
            log.error("get users failed\n" + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        try {
            log.info("trying to delete user with username: {}", username);
            if (!userRepository.existsByUsername(username)) {
                throw new ResourceNotFoundException("user", "username", username);
            }
            userRepository.deleteByUsername(username);
            log.info("delete user with username: {} successfully", username);
        } catch (Exception e) {
            log.error("delete user with username: {} failed\n", username + e.getMessage());
            throw e;
        }
    }
}
