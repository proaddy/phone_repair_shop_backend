package com.example.usermanagement.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usermanagement.RoleEntity;
import com.example.usermanagement.UserEntity;
import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.dto.UserUpdateDTO;
import com.example.usermanagement.repository.RoleRepository;
import com.example.usermanagement.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired	
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordService passwordService;
	
	public UserEntity createUser(UserDTO userdto) {
		UserEntity user = new UserEntity();
		
		// setting basic user details
		user.setUsername(userdto.getUsername());
		user.setEmail(userdto.getEmail());
		user.setPassword(passwordService.hashPassword(userdto.getPassword())); // hasing the password
		
		RoleEntity role = roleRepository.findByRoleName("USER")
										.orElseGet(()->{
											RoleEntity newRole = new RoleEntity();
											newRole.setName("USEr");
											newRole.setDescription("Default role for normal user");
											return roleRepository.save(newRole);
										});        
        user.setRole(role);
        
        // save the user entity
		return userRepository.save(user);
	}
	
	public boolean verifyUserPassword(String plainPassword, String username) {
	    UserEntity user = userRepository.findByUsername(username);
	    return passwordService.validatePassword(plainPassword, user.getPassword());
	}

	public UserEntity updateUser(Long id, UserUpdateDTO updatedto) {
	    UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	    if (updatedto.getEmail() != null) {
	        user.setEmail(updatedto.getEmail());
	    }
	    if (updatedto.getPassword() != null) {
	        user.setPassword(passwordService.hashPassword(updatedto.getPassword()));
	    }
	    return userRepository.save(user);
	}
	
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}
}
