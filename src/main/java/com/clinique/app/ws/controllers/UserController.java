package com.clinique.app.ws.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.requests.UserRequest;
import com.clinique.app.ws.responses.UserResponse;
import com.clinique.app.ws.responses.errors.ErrorMessages;
import com.clinique.app.ws.services.UserService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController 
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService ; //injection de dependance
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> getUser(Principal principal, @PathVariable String id) {
		UserDto currentUser = userService.getUserByUserId(principal.getName());
		
		if(!currentUser.getRole().getName().equals("admin")) {
			id = principal.getName();
		}
			
		UserDto userDto = userService.getUserByUserId(id);
		ModelMapper modelMapper = new ModelMapper();
		UserResponse userResponse = modelMapper.map(userDto, UserResponse.class); //Copier vers la reponse
		return new ResponseEntity<>(userResponse,HttpStatus.OK);

	}
	
	@GetMapping
	public ResponseEntity<Object> getAllUsers(@RequestParam(value="page", defaultValue = "1") int page ,@RequestParam(value="limit",defaultValue = "4") int limit){
		
		List<UserResponse> userResponse = new ArrayList<>();

		//UserDto currentUser = userService.getUserByUserId(principal.getName());
		//if(currentUser.getRole().getName().equals("admin")) {
			List<UserDto> users =  userService.getUsers(page,limit);
			for (UserDto userDto: users) {
				ModelMapper modelMapper = new ModelMapper();
				UserResponse user = modelMapper.map(userDto, UserResponse.class);
				userResponse.add(user);
			}
			return new ResponseEntity<>(userResponse,HttpStatus.OK) ; 
		//}
		
		
		//return new ResponseEntity<>(userResponse,HttpStatus.UNAUTHORIZED) ; 
	}
	
	@PostMapping
	public ResponseEntity<Object> createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {
				
		if(userRequest.getFirstName().isEmpty() ) throw new UserException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		//UserDto currentUser = userService.getUserByUserId(principal.getName());
		
		//if(currentUser.getRole().getName().equals("admin")) {
			ModelMapper modelMapper = new ModelMapper();
			UserDto userDto = modelMapper.map(userRequest, UserDto.class);
			
			UserDto createUser = userService.createUser(userDto); 		
			UserResponse userResponse = modelMapper.map(createUser, UserResponse.class);

			
			return new ResponseEntity<>(userResponse,HttpStatus.CREATED);
		//}
		
		//return new ResponseEntity<>(HttpStatus.UNAUTHORIZED) ; 

	}
	

	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Object> updateUser(Principal principal , @PathVariable String id, @RequestBody UserRequest userRequest) {
		
		UserDto currentUser = userService.getUserByUserId(principal.getName());
		
		if(currentUser.getRole().getName().equals("admin")) {
			ModelMapper modelMapper = new ModelMapper();
			UserDto userDto = modelMapper.map(userRequest, UserDto.class);
			
			UserDto updateUser = userService.updateUser(userDto,id); //Appel au service
			
			UserResponse userResponse = modelMapper.map(updateUser, UserResponse.class); //Copier vers la reponse
			
			return new ResponseEntity<>(userResponse,HttpStatus.ACCEPTED) ; 
		}
		List<UserResponse> userResponse = new ArrayList<>();

		return new ResponseEntity<>(userResponse,HttpStatus.UNAUTHORIZED) ; 
		
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> deleteUser(Principal principal , @PathVariable String id) {
		
		UserDto currentUser = userService.getUserByUserId(principal.getName());
		if(currentUser.getRole().getName().equals("admin")) {
			userService.deleteUser(id);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		
		
	}
}
