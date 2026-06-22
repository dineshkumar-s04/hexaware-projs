package com.hexaware.springrest.datajpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.springrest.datajpa.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    public List<User> findByUsername(String username);

    public List<User> findByRole(String role);

    public User findByUsernameAndPassword(String username, String password);

}