package com.hexaware.springrest.datajpa.service;

import java.util.List;

import com.hexaware.springrest.datajpa.entity.User;

public interface IUserService {

    public User addUser(User user);

    public User updateUser(User user);

    public User getByUserId(int userId);

    public void deleteByUserId(int userId);

    public List<User> getAllUsers();

    public List<User> findByUsername(String username);

    public List<User> findByRole(String role);

    public User findByUsernameAndPassword(String username, String password);

}