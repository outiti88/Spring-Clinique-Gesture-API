package com.clinique.app.ws.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.requests.UserRequest;
import com.clinique.app.ws.responses.UserResponse;
import com.clinique.app.ws.services.UserService;

@RestController 
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService ; //injection de dependance
	
	@GetMapping
	public String getUser() {
		return "get user";
	}
	
	@PostMapping
	public UserResponse createUser(@RequestBody UserRequest userRequest) {
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userRequest, userDto); //couche presentation 
		
		UserDto createUser = userService.createUser(userDto); //Appel au service
		
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(createUser, userResponse); //Copier vers la reponse
		
		return userResponse;
	}
	
	@PutMapping
	public String updateUser() {
		return "update user";
	}
	
	@DeleteMapping
	public String deleteUser() {
		return "delete user";
	}
}
