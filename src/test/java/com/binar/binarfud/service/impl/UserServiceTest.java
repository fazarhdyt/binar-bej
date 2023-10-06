package com.binar.binarfud.service.impl;

import com.binar.binarfud.model.User;
import com.binar.binarfud.repository.UserRepository;
import com.binar.binarfud.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUser_success() {

        User userToSave = new User();
        userToSave.setUsername("testuser");
        userToSave.setEmail("testuser@example.com");

        // Act
        User createdUser = userService.createUser(userToSave);

        // Assert
        assertNotNull(createdUser.getId());
        assertEquals(userToSave.getUsername(), createdUser.getUsername());
        assertEquals(userToSave.getEmail(), createdUser.getEmail());
    }

    @Test
    public void testGetUserByUsername_userExists() {

        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        userRepository.save(user);

        // Act
        User result = userService.getUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    public void testGetUserByUsername_userNotFound() {

        String username = "nonExistentUser";

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.getUserByUsername(username));
    }

    @Test
    public void testUpdateUserByUsername_userExists() {

        String username = "existingUser";
        User existingUser = new User();
        existingUser.setUsername(username);
        userRepository.save(existingUser);

        User updatedUser = new User();
        updatedUser.setUsername(username);
        updatedUser.setEmail("updated@example.com");
        updatedUser.setPassword("newPassword");

        // Act
        User result = userService.updateUserByUsername(username, updatedUser);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        assertEquals(updatedUser.getPassword(), result.getPassword());

        // Verify that the user has been updated in the database
        Optional<User> updatedUserInDb = userRepository.findById(existingUser.getId());
        assertTrue(updatedUserInDb.isPresent());
        assertEquals(updatedUser.getEmail(), updatedUserInDb.get().getEmail());
        assertEquals(updatedUser.getPassword(), updatedUserInDb.get().getPassword());
    }

    @Test
    public void testUpdateUserByUsername_userNotFound() {

        String username = "nonExistentUser";
        User updatedUser = new User();

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.updateUserByUsername(username, updatedUser));
    }

    @Test
    public void testGetUsers_nonEmptyList() {

        User user1 = new User();
        User user2 = new User();
        userRepository.saveAll(Arrays.asList(user1, user2));

        // Act
        List<User> result = userService.getUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetUsers_emptyList() {
        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.getUsers());
    }

    @Test
    public void testDeleteUserByUsername_userExists() {

        String username = "existingUser";
        User existingUser = new User();
        existingUser.setUsername(username);
        userService.createUser(existingUser);

        // Act
        userService.deleteUserByUsername(username);

        // Assert
        // Verify that the user has been deleted from the database
        Optional<User> deletedUserInDb = userRepository.getUserByUsername(username);
        assertFalse(deletedUserInDb.isPresent());
    }

    @Test
    public void testDeleteUserByUsername_userNotFound() {

        String username = "nonExistentUser";

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.deleteUserByUsername(username));
    }

}
