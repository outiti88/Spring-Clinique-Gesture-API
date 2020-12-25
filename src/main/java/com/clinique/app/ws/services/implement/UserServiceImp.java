package com.clinique.app.ws.services.implement;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.clinique.app.ws.dto.AdresseDto;
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

		userDto.getRole().setContactId(util.generateStringId(30));
		userDto.getRole().setUser(userDto);
		
		for (int i = 0; i < userDto.getAdresses().size(); i++) {
			AdresseDto adresseDto = userDto.getAdresses().get(i);
			adresseDto.setUser(userDto);
			adresseDto.setAdresseId(util.generateStringId(30));
			userDto.getAdresses().set(i, adresseDto);
		}
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity =  modelMapper.map(userDto, UserEntity.class);
		
		//System.out.println(userDto);
		
		//BeanUtils.copyProperties(userDto, userEntity);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		userEntity.setUserID(util.generateStringId(32));
		
		UserEntity newUserEntity =  userRepository.save(userEntity);
		
		UserDto newUserDto = modelMapper.map(newUserEntity, UserDto.class);

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
		//ModelMapper modelMapper = new ModelMapper();
		BeanUtils.copyProperties(userDto, userEntity);
		//UserDto userDto =  modelMapper.map(userEntity, UserDto.class);
		
		return userDto;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserID(userId);
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto =  modelMapper.map(userEntity, UserDto.class);
		
		return userDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		UserEntity userEntity = userRepository.findByUserID(userId);
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		
		userEntity.setLastName(userDto.getLastName());
		userEntity.setFirstName(userDto.getFirstName());
		
		UserEntity userUpdated = userRepository.save(userEntity);
		
		ModelMapper modelMapper = new ModelMapper();
		UserDto user =  modelMapper.map(userUpdated, UserDto.class);
		
		
		return user;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserID(userId);
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		
		userRepository.delete(userEntity);
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		
		if (page>0) page -- ;
		
		PageRequest pageableRequest = PageRequest.of(page,limit);
		
		Page<UserEntity> userPage = userRepository.findAll(pageableRequest);
		
		List<UserEntity> users = userPage.getContent();
		
		List<UserDto> usersDtos = new ArrayList<>();
		
		for (UserEntity user: users) {
			ModelMapper modelMapper = new ModelMapper();
			UserDto userDto =  modelMapper.map(user, UserDto.class);
			 //Copier vers la reponse
			
			usersDtos.add(userDto);
		}
		
		return usersDtos;
	}

}