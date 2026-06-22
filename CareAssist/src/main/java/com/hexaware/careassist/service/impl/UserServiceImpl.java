package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careassist.dto.UserDTO;
import com.hexaware.careassist.entity.User;
import com.hexaware.careassist.exception.UserNotFoundException;
import com.hexaware.careassist.repository.UserRepository;
import com.hexaware.careassist.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
    private UserRepository userRepository;

	@Override
	public UserDTO registerUser(UserDTO userDTO) {

	    User user = new User();

	    user.setName(userDTO.getName());
	    user.setEmail(userDTO.getEmail());
	    user.setPassword(userDTO.getPassword());
	    user.setPhone(userDTO.getPhone());
	    user.setRole(userDTO.getRole());
	    user.setAccountStatus(userDTO.getAccountStatus());
	    user.setProfilePic(userDTO.getProfilePic());

	    User savedUser = userRepository.save(user);

	    UserDTO responseDTO = new UserDTO();

	    responseDTO.setUserId(savedUser.getUserId());
	    responseDTO.setName(savedUser.getName());
	    responseDTO.setEmail(savedUser.getEmail());
	    responseDTO.setPassword(savedUser.getPassword());
	    responseDTO.setPhone(savedUser.getPhone());
	    responseDTO.setRole(savedUser.getRole());
	    responseDTO.setAccountStatus(savedUser.getAccountStatus());
	    responseDTO.setProfilePic(savedUser.getProfilePic());

	    return responseDTO;
	}

	@Override
	public UserDTO getUserById(Integer userId) {

	    User user = userRepository.findById(userId)
	            .orElseThrow(() ->
	                    new UserNotFoundException(
	                            "User not found with ID: " + userId));

	    UserDTO userDTO = new UserDTO();

	    userDTO.setUserId(user.getUserId());
	    userDTO.setName(user.getName());
	    userDTO.setEmail(user.getEmail());
	    userDTO.setPassword(user.getPassword());
	    userDTO.setPhone(user.getPhone());
	    userDTO.setRole(user.getRole());
	    userDTO.setAccountStatus(user.getAccountStatus());
	    userDTO.setProfilePic(user.getProfilePic());

	    return userDTO;
	}

	@Override
	public List<UserDTO> getAllUsers() {

	    List<User> users = userRepository.findAll();

	    List<UserDTO> userDTOList = new ArrayList<>();

	    for (User user : users) {

	        UserDTO userDTO = new UserDTO();

	        userDTO.setUserId(user.getUserId());
	        userDTO.setName(user.getName());
	        userDTO.setEmail(user.getEmail());
	        userDTO.setPassword(user.getPassword());
	        userDTO.setPhone(user.getPhone());
	        userDTO.setRole(user.getRole());
	        userDTO.setAccountStatus(user.getAccountStatus());
	        userDTO.setProfilePic(user.getProfilePic());

	        userDTOList.add(userDTO);
	    }

	    return userDTOList;
	}

	@Override
	public UserDTO updateUser(Integer userId, UserDTO userDTO) {

	    User user = userRepository.findById(userId)
	            .orElseThrow(() ->
	                    new UserNotFoundException(
	                            "User not found with ID: " + userId));

	    user.setName(userDTO.getName());
	    user.setEmail(userDTO.getEmail());
	    user.setPassword(userDTO.getPassword());
	    user.setPhone(userDTO.getPhone());
	    user.setRole(userDTO.getRole());
	    user.setAccountStatus(userDTO.getAccountStatus());
	    user.setProfilePic(userDTO.getProfilePic());

	    User updatedUser = userRepository.save(user);

	    UserDTO responseDTO = new UserDTO();

	    responseDTO.setUserId(updatedUser.getUserId());
	    responseDTO.setName(updatedUser.getName());
	    responseDTO.setEmail(updatedUser.getEmail());
	    responseDTO.setPassword(updatedUser.getPassword());
	    responseDTO.setPhone(updatedUser.getPhone());
	    responseDTO.setRole(updatedUser.getRole());
	    responseDTO.setAccountStatus(updatedUser.getAccountStatus());
	    responseDTO.setProfilePic(updatedUser.getProfilePic());

	    return responseDTO;
	}

	@Override
	public void deleteUser(Integer userId) {

	    User user = userRepository.findById(userId)
	            .orElseThrow(() ->
	                    new UserNotFoundException(
	                            "User not found with ID: " + userId));

	    userRepository.delete(user);
	}
}
