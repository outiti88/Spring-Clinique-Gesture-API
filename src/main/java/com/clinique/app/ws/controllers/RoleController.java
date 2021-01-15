package com.clinique.app.ws.controllers;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinique.app.ws.dto.RoleDto;
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.requests.RoleRequest;
import com.clinique.app.ws.responses.RoleResponse;
import com.clinique.app.ws.responses.errors.ErrorMessages;
import com.clinique.app.ws.services.RoleService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController 
@RequestMapping("/roles")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
	
	@PostMapping
	public ResponseEntity<Object> createRole(@RequestBody @Valid RoleRequest roleRequest) throws Exception {
		
		if(roleRequest.getName().isEmpty()) throw new UserException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		RoleDto roleDto = new RoleDto();
		BeanUtils.copyProperties(roleRequest, roleDto);
		RoleDto createRole = roleService.createRole(roleDto);
		RoleResponse roleResponse = new RoleResponse();
		BeanUtils.copyProperties(createRole, roleResponse);
		return new ResponseEntity<>(roleResponse,HttpStatus.CREATED); 
		
	}

}
