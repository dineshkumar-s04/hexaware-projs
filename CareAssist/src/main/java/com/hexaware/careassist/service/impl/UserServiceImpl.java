package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.careassist.dto.UserDTO;
import com.hexaware.careassist.entity.User;
import com.hexaware.careassist.exception.UserNotFoundException;
import com.hexaware.careassist.repository.UserRepository;
import com.hexaware.careassist.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	private static final Logger logger =
            LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDTO registerUser(UserDTO userDTO) {

		logger.info("Registering user with email {}", userDTO.getEmail());
		
	    User user = new User();

	    user.setName(userDTO.getName());
	    user.setEmail(userDTO.getEmail());
	    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
	    user.setPhone(userDTO.getPhone());
	    user.setRole(userDTO.getRole());
	    user.setAccountStatus(userDTO.getAccountStatus());
	    user.setProfilePic(userDTO.getProfilePic());

	    User savedUser = userRepository.save(user);
	    
	    logger.info("User registered successfully with id {}", savedUser.getUserId());

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

		logger.info("Fetching user with id {}", userId);
		
	    User user = userRepository.findById(userId)
	    		.orElseThrow(() -> {

	    		    logger.warn("User not found with id {}", userId);

	    		    return new UserNotFoundException(
	    		            "User not found with ID: " + userId);
	    		});

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

		logger.info("Fetching all users");
		
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
	    
	    logger.info("Total users fetched: {}", userDTOList.size());

	    return userDTOList;
	}

	@Override
	public UserDTO updateUser(Integer userId, UserDTO userDTO) {
		
		logger.info("Updating user with id {}", userId);

	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> {
	                
	                logger.warn("User not found with id {}", userId);

	                return new UserNotFoundException(
	                        "User not found with ID: " + userId);
	            });

	    user.setName(userDTO.getName());
	    user.setEmail(userDTO.getEmail());
	    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
	    user.setPhone(userDTO.getPhone());
	    user.setRole(userDTO.getRole());
	    user.setAccountStatus(userDTO.getAccountStatus());
	    user.setProfilePic(userDTO.getProfilePic());

	    User updatedUser = userRepository.save(user);

	    logger.info("User updated successfully with id {}", updatedUser.getUserId());

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

		logger.info("Deleting user with id {}", userId);
		
		User user = userRepository.findById(userId)
		        .orElseThrow(() -> {

		            logger.warn("User not found with id {}", userId);

		            return new UserNotFoundException(
		                    "User not found with ID: " + userId);
		        });

		userRepository.delete(user);

		logger.info("User deleted successfully with id {}", userId);
	}
}
