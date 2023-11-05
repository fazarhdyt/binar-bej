package com.binar.binarfud.service.impl;

import com.binar.binarfud.dto.UserDto;
import com.binar.binarfud.exception.ResourceNotFoundException;
import com.binar.binarfud.model.User;
import com.binar.binarfud.repository.UserRepository;
import com.binar.binarfud.service.EntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser_success() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.save(user)).thenReturn(user);

        UserDto result = userService.createUser(user);

        verify(userRepository, times(1)).save(user);
        assertEquals(user.getUsername(), result.getUsername());
        assertNotNull(result);
    }

    @Test
    public void testGetUserByUsername_success() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.getUserByUsername(username)).thenReturn(Optional.of(user));

        UserDto result = userService.getUserByUsername(username);

        assertEquals(username, result.getUsername());
    }

    @Test
    public void testGetUserByUsername_userNotFound() {
        String username = "nonExistentUser";

        when(userRepository.existsByUsername(username)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.getUserByUsername(username));
    }

    @Test
    public void testUpdateUserByUsername_success() {
        String username = "testUser";
        User existingUser = new User();
        existingUser.setUsername(username);

        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.getUserByUsername(username)).thenReturn(Optional.of(existingUser));

        User updatedUser = new User();
        updatedUser.setUsername("updatedUser");

        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.getUserByUsername(username)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserDto result = userService.updateUserByUsername(username, updatedUser);

        assertEquals(result, EntityMapper.userToUserDto(existingUser));
        assertEquals(existingUser.getUsername(), "updatedUser");
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testUpdateUserByUsername_userNotFound() {
        String username = "nonExistentUser";
        User updatedUser = new User();
        updatedUser.setUsername("updatedUsername");

        when(userRepository.existsByUsername(username)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUserByUsername(username, updatedUser));
    }

    @Test
    public void testGetUsers_success() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        when(userRepository.findAll()).thenReturn(userList);

        List<UserDto> result = userService.getUsers();

        assertEquals(result.size(), userList.size());
        for (int i = 0; i < userList.size(); i++) {
            assertEquals(result.get(i), EntityMapper.userToUserDto(userList.get(i)));
        }
    }

    @Test
    public void testGetUsers_emptyList() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(RuntimeException.class, () -> userService.getUsers());
    }

    @Test
    public void testDeleteUserByUsername_success() {
        String username = "testUser";

        when(userRepository.existsByUsername(username)).thenReturn(true);

        userService.deleteUserByUsername(username);

        verify(userRepository, times(1)).deleteByUsername(username);
    }

    @Test
    public void testDeleteUserByUsername_userNotFound() {
        String username = "nonExistentUser";

        when(userRepository.existsByUsername(username)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUserByUsername(username));
    }

}
