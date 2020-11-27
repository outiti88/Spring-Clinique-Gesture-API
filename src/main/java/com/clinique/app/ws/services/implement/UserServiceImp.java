package com.clinique.app.ws.services.implement;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.entities.UserEntity;
import com.clinique.app.ws.repositories.UserRepository;
import com.clinique.app.ws.services.UserService;
import com.clinique.app.ws.shared.Utils;

@Service
public class UserServiceImp implements UserService {
	
	@Autowired
	 UserRepository userRepository ;
	
	@Autowired
	Utils util ;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		UserEntity userCheck = userRepository.findByEmail(userDto.getEmail());
		
		if(userCheck!=null) throw new RuntimeException("L'utilisateur existe déjà");

		UserEntity userEntity = new UserEntity() ;
		
		BeanUtils.copyProperties(userDto, userEntity);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		userEntity.setUserID(util.generateUserId(32));
		UserEntity newUserEntity =  userRepository.save(userEntity);
		
		UserDto newUserDto = new UserDto();
		BeanUtils.copyProperties(newUserEntity, newUserDto);
		
		return newUserDto;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);
		
		return userDto;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserID(userId);
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);
		
		return userDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		UserEntity userEntity = userRepository.findByUserID(userId);
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		
		userEntity.setLastName(userDto.getLastName());
		userEntity.setFirstName(userDto.getFirstName());
		
		UserEntity userUpdated = userRepository.save(userEntity);
		
		UserDto user = new UserDto();
		BeanUtils.copyProperties(userUpdated, user);
		
		return user;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserID(userId);
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		
		userRepository.delete(userEntity);
	}

}