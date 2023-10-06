package com.binar.binarfud.service;

import com.binar.binarfud.model.User;

import java.util.List;

public interface IUserService {

    User createUser(User user);

    User getUserByUsername(String username);

    User updateUserByUsername(String username, User user);

    List<User> getUsers();

    void deleteUserByUsername(String username);
}
