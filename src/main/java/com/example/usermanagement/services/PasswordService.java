package com.example.usermanagement.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usermanagement.repository.UserRepository;

@Service
public class PasswordService {
	private final BCryptPasswordEncoder passwordEncoder;
	
	public PasswordService() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public String hashPassword(String password) { // encrypt
		return passwordEncoder.encode(password);
	}
	
	public boolean validatePassword(String plainPassword, String hashedPassword) { // decrypt
		return passwordEncoder.matches(plainPassword, hashedPassword);
	}
	
	public passwordResetService(UserRepository userRepository, EmailService emailService, )
}
