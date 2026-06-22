package com.hexaware.careassist.service;

import java.util.List;
import com.hexaware.careassist.dto.UserDTO;

public interface IUserService {

    UserDTO registerUser(UserDTO userDTO);

    UserDTO getUserById(Integer userId);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Integer userId, UserDTO userDTO);

    void deleteUser(Integer userId);
}