package com.binar.binarfud.controller;

import com.binar.binarfud.dto.ResponseData;
import com.binar.binarfud.exception.ProcessException;
import com.binar.binarfud.model.User;
import com.binar.binarfud.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {

        try {
            return ResponseData.statusResponse(userService.createUser(user), HttpStatus.OK, "success add user");
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @GetMapping()
    public ResponseEntity<Object> getUsers() {

        try {
            return ResponseData.statusResponse(userService.getUsers(), HttpStatus.OK, "success get users");
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @PutMapping("/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable String username, @Valid @RequestBody User user) {

        try {
            userService.updateUserByUsername(username, user);
            return ResponseData.statusResponse(userService.getUserByUsername(username), HttpStatus.OK, "success update user");
        } catch (ProcessException e) {
            return ResponseData.statusResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username) {

        try {
            userService.deleteUserByUsername(username);
            return ResponseData.statusResponse(null, HttpStatus.OK, "success delete user");
        } catch (ProcessException e) {
            return ResponseData.statusResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }
    }
}
