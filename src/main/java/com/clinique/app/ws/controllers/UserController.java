package com.clinique.app.ws.controllers;

import org.springframework.beans.BeanUtils;
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

import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.requests.UserRequest;
import com.clinique.app.ws.responses.UserResponse;
import com.clinique.app.ws.responses.errors.ErrorMessages;
import com.clinique.app.ws.services.UserService;

@RestController 
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService ; //injection de dependance
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> getUser(@PathVariable String id) {
		
		UserDto userDto = userService.getUserByUserId(id);
		
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(userDto, userResponse); //Copier vers la reponse
		
		return new ResponseEntity<>(userResponse,HttpStatus.OK) ; 
	}
	
	
	
	@PostMapping
	public ResponseEntity<Object> createUser(@RequestBody UserRequest userRequest) throws Exception {
		
		if(userRequest.getFirstName().isEmpty() ) throw new UserException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userRequest, userDto); //couche presentation 
		
		UserDto createUser = userService.createUser(userDto); //Appel au service
		
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(createUser, userResponse); //Copier vers la reponse
		
		return new ResponseEntity<>(userResponse,HttpStatus.CREATED) ; 

	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable String id, @RequestBody UserRequest userRequest) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userRequest, userDto); //couche presentation 
		
		UserDto updateUser = userService.updateUser(userDto,id); //Appel au service
		
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(updateUser, userResponse); //Copier vers la reponse
		
		return new ResponseEntity<>(userResponse,HttpStatus.ACCEPTED) ; 
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable String id) {
		
		userService.deleteUser(id);
	
		return new ResponseEntity<>(HttpStatus.NO_CONTENT) ; 
	}
}
