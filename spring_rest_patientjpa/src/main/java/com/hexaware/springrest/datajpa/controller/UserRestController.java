package com.hexaware.springrest.datajpa.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.springrest.datajpa.entity.User;
import com.hexaware.springrest.datajpa.service.IUserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/users")
@Slf4j
@AllArgsConstructor
public class UserRestController {

    IUserService service;

    static {
        log.info("User Controller Initialized");
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return service.updateUser(user);
    }

    @GetMapping("/getbyid/{userId}")
    public User getByUserId(@PathVariable int userId) {
        return service.getByUserId(userId);
    }

    @GetMapping("/getall")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {

        service.deleteByUserId(userId);

        return new ResponseEntity<>("User Deleted Successfully", HttpStatus.ACCEPTED);
    }

    @GetMapping("/searchusername/{username}")
    public List<User> findByUsername(@PathVariable String username) {
        return service.findByUsername(username);
    }

    @GetMapping("/searchrole/{role}")
    public List<User> findByRole(@PathVariable String role) {
        return service.findByRole(role);
    }

    @GetMapping("/login/{username}/{password}")
    public User login(@PathVariable String username,
                      @PathVariable String password) {

        return service.findByUsernameAndPassword(username, password);
    }
}