package com.hexaware.careassist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.careassist.dto.UserDTO;
import com.hexaware.careassist.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {

		UserDTO savedUser = userService.registerUser(userDTO);

		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId) {

		UserDTO user = userService.getUserById(userId);

		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {

		List<UserDTO> users = userService.getAllUsers();

		return ResponseEntity.ok(users);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Integer userId, @RequestBody UserDTO userDTO) {

		UserDTO updatedUser = userService.updateUser(userId, userDTO);

		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {

		userService.deleteUser(userId);

		return ResponseEntity.ok("User deleted successfully");
	}
}