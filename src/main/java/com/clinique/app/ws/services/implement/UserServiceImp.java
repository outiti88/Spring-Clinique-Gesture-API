package com.clinique.app.ws.services.implement;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.entities.UserEntity;
import com.clinique.app.ws.repositories.UserRepository;
import com.clinique.app.ws.services.UserService;

@Service
public class UserServiceImp implements UserService {
	
	@Autowired
	 UserRepository userRepository ;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		UserEntity userCheck = userRepository.findByEmail(userDto.getEmail());
		
		if(userCheck!=null) throw new RuntimeException("L'utilisateur existe déjà");

		UserEntity userEntity = new UserEntity() ;
		
		BeanUtils.copyProperties(userDto, userEntity);
		userEntity.setEncryptedPassword("test");
		userEntity.setUserID("5");
		UserEntity newUserEntity =  userRepository.save(userEntity);
		
		UserDto newUserDto = new UserDto();
		BeanUtils.copyProperties(newUserEntity, newUserDto);
		
		return newUserDto;
	}

}