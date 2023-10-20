package com.binar.binarfud.service;

import com.binar.binarfud.dto.UserDto;
import com.binar.binarfud.model.User;

import java.util.List;

public interface IUserService {

    UserDto createUser(User user);

    UserDto getUserByUsername(String username);

    UserDto updateUserByUsername(String username, User user);

    List<UserDto> getUsers();

    void deleteUserByUsername(String username);
}
