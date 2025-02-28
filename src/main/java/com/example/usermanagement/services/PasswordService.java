package com.example.usermanagement.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usermanagement.PasswordResetToken;
import com.example.usermanagement.UserEntity;
import com.example.usermanagement.repository.PasswordRepository;
import com.example.usermanagement.repository.UserRepository;

@Service
public class PasswordService {
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordRepository passwordRepository;
	
	public PasswordService() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public String hashPassword(String password) { // encrypt
		return passwordEncoder.encode(password);
	}
	
	public boolean validatePassword(String plainPassword, String hashedPassword) { // decrypt
		return passwordEncoder.matches(plainPassword, hashedPassword);
	}
	
	public String initiatePasswordReset(String email) {
		try {
			UserEntity user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("User not found with email: " + email));
			
			String token = UUID.randomUUID().toString();
			LocalDateTime expirationDate = LocalDateTime.now().plusHours(1); // token is valid for only 1 hour
			
			PasswordResetToken resetToken = new PasswordResetToken();
			resetToken.setToken(token);
			resetToken.setExpirationDate(expirationDate);
			resetToken.setUser(user);
			
			passwordRepository.save(resetToken); // saving token in database
			
			return "Password reset token " + token;				
		} catch (Exception e) {
			return "The exception occured is " + e;
		}
	}
	
	public String confirmPasswordReset(String token, String newPassword) {
		try {
			PasswordResetToken resetToken = passwordRepository.findByToken(token).orElseThrow(()-> new IllegalArgumentException("Invalid or expired token"));
			
			if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
				throw new IllegalArgumentException("Expired token");
			}			

			UserEntity user = resetToken.getUser();
			user.setPassword(hashPassword(newPassword));
			userRepository.save(user);
			
			// invalidate the token
			passwordRepository.delete(resetToken); // deleting the expired/used token
			
			return "Password successfully reset for the user ";
		} catch (Exception e) {
			return "The exception occured is " + e;
		}	
	}
}