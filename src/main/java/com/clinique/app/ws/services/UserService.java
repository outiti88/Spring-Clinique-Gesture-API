package com.clinique.app.ws.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.clinique.app.ws.dto.UserDto;

public interface UserService extends UserDetailsService{
	
	public UserDto createUser(UserDto userDto);
	
	public UserDto getUser(String email);
	
	public UserDto getUserByUserId(String userId);
	
	public UserDto updateUser(UserDto userDto,String userId);
	
	public void deleteUser(String userId);
	
	public List<UserDto> getUsers(int page, int limit);
	
	public List<UserDto> getUsersByRole(String roleId);

}
