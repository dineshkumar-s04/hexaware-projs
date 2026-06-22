package com.hexaware.springrest.datajpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.springrest.datajpa.entity.User;
import com.hexaware.springrest.datajpa.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImp implements IUserService {

    @Autowired
    UserRepository repo;

    @Override
    public User addUser(User user) {

        log.info("Adding User");

        return repo.save(user);
    }

    @Override
    public User updateUser(User user) {

        log.info("Updating User");

        return repo.save(user);
    }

    @Override
    public User getByUserId(int userId) {

        return repo.findById(userId).orElse(null);
    }

    @Override
    public void deleteByUserId(int userId) {

        repo.deleteById(userId);
    }

    @Override
    public List<User> getAllUsers() {

        log.info("Displaying all users");

        return repo.findAll();
    }

    @Override
    public List<User> findByUsername(String username) {

        return repo.findByUsername(username);
    }

    @Override
    public List<User> findByRole(String role) {

        return repo.findByRole(role);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {

        return repo.findByUsernameAndPassword(username, password);
    }

}