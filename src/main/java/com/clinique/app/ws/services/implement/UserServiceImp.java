package com.clinique.app.ws.services.implement;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.clinique.app.ws.dto.PatientDto;
import com.clinique.app.ws.dto.RoleDto;
import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.entities.PatientEntity;
import com.clinique.app.ws.entities.RoleEntity;
import com.clinique.app.ws.entities.UserEntity;
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.repositories.RoleRepository;
import com.clinique.app.ws.repositories.UserRepository;
import com.clinique.app.ws.responses.errors.ErrorMessages;
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
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		UserEntity userCheck = userRepository.findByEmail(userDto.getEmail());
		
		if(userCheck!=null) throw new RuntimeException("L'utilisateur existe déjà");
		
		RoleEntity roleEntity = roleRepository.findByName(userDto.getRole().getName());
		
		if(roleEntity == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		RoleDto roleDto = new RoleDto();
		
		BeanUtils.copyProperties(roleEntity, roleDto);

		userDto.setRole(roleDto);
		
		
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
		
		userEntity = userRepository.findByUserID(userEntity.getUserID());

		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto =  modelMapper.map(userEntity, UserDto.class);
		
		return userDto;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserID(userId);
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		UserDto userDto =  new UserDto();
		userDto.setEmail(userEntity.getEmail());
		userDto.setUserID(userEntity.getUserID());
		userDto.setLastName(userEntity.getLastName());
		userDto.setFirstName(userEntity.getLastName());
		RoleDto roleDto = new RoleDto();
		roleDto.setName(userEntity.getRole().getName());
		roleDto.setRoleId(userEntity.getRole().getRoleId());
		Iterator<PatientEntity> iterator = userEntity.getPatients().iterator();
		List<PatientDto> patientDtos = new ArrayList<>();
		while (iterator.hasNext()) {
			PatientEntity patientEntity = iterator.next();
			PatientDto patientDto = new PatientDto();
			patientDto.setAdresse(patientEntity.getAdresse());
			patientDto.setCin(patientEntity.getCin());
			patientDto.setNom(patientEntity.getNom());
			patientDto.setPatientId(patientEntity.getPatientId());
			patientDto.setPrenom(patientEntity.getprenom());
			patientDto.setTelephone(patientEntity.getTelephone());
			patientDtos.add(patientDto);
		}
		userDto.setRole(roleDto);
		userDto.setPatientDto(patientDtos);
		return userDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		UserEntity userEntity = userRepository.findByUserID(userId);
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		
		userEntity.setLastName(userDto.getLastName());
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setEmail(userDto.getEmail());
		RoleEntity role = roleRepository.findByName(userDto.getRole().getName());
		if(role == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		userEntity.setRole(role);
		
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



	@Override
	public List<UserDto> getUsersByRole(String roleId) {
		RoleEntity roleEntity = roleRepository.findByRoleId(roleId);
		List<UserEntity> users = userRepository.findByRole(roleEntity);
		List<UserDto> userDtos = new ArrayList<>();
		Iterator<UserEntity> iterator = users.iterator();
		while (iterator.hasNext()) {
			UserEntity userEntity = iterator.next();
			UserDto userDto = new UserDto();
			userDto.setEmail(userEntity.getEmail());
			userDto.setUserID(userEntity.getUserID());
			userDto.setLastName(userEntity.getLastName());
			userDto.setFirstName(userEntity.getLastName());
			RoleDto roleDto = new RoleDto();
			roleDto.setName(userEntity.getRole().getName());
			roleDto.setRoleId(userEntity.getRole().getRoleId());
			Iterator<PatientEntity> patientIterator = userEntity.getPatients().iterator();
			List<PatientDto> patientDtos = new ArrayList<>();
			while (iterator.hasNext()) {
				PatientEntity patientEntity = patientIterator.next();
				PatientDto patientDto = new PatientDto();
				patientDto.setAdresse(patientEntity.getAdresse());
				patientDto.setCin(patientEntity.getCin());
				patientDto.setNom(patientEntity.getNom());
				patientDto.setPatientId(patientEntity.getPatientId());
				patientDto.setPrenom(patientEntity.getprenom());
				patientDto.setTelephone(patientEntity.getTelephone());
				patientDtos.add(patientDto);
			}
			userDto.setRole(roleDto);
			userDto.setPatientDto(patientDtos);
			userDtos.add(userDto);
		}
		return userDtos;
	}

}