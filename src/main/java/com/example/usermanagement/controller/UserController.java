package com.example.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.services.UserService;
import com.example.usermanagement.UserEntity;
import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.dto.UserLoginDTO;
import com.example.usermanagement.dto.UserResponseDTO;
import com.example.usermanagement.dto.UserUpdateDTO;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@PutMapping("/{id}")
	public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO updateDto) {
	    UserEntity updatedUser = userService.updateUser(id, updateDto);
	    UserResponseDTO responseDto = new UserResponseDTO();
	    responseDto.setUsername(updatedUser.getUsername());
	    responseDto.setEmail(updatedUser.getEmail());
	    responseDto.setCreatedDate(updatedUser.getCreated_at());
	    return responseDto;
	}
	
	// ---------- Authentication APIs ----------
	@PostMapping("/api/auth/register")
	public UserResponseDTO registerUser(@RequestBody UserDTO userdto) {
		// entity
	    UserEntity savedUser = userService.createUser(userdto);
	    
	    // response to give for confirmation
	    UserResponseDTO responsedto = new UserResponseDTO();
	    
	    responsedto.setUsername(savedUser.getUsername());
	    responsedto.setEmail(savedUser.getEmail());
	    responsedto.setCreatedDate(savedUser.getCreated_at());
	    return responsedto;
	}
	
	@PostMapping("/api/auth/login")
	public String loginUser(@RequestBody UserLoginDTO loginDto) {
	    try {
	        boolean isValid = userService.verifyUserPassword(loginDto.getPassword(), loginDto.getUsername());
	        if (isValid) {
	            return "Login successful";
	        } else {
	            return "Invalid username or password";
	        }
	    } catch (Exception e) {
	        System.out.println("Some Error occured");
	        return "Error -> " + e.getMessage();
	    }
	}
	
	
	@PostMapping("/api/auth/password-reset/request")
	public UserResponseDTO<String> requestPasswordReset(@RequestBody)
	
//	@PostMapping("/api/auth/logout") // optional
	@PostMapping("/api/auth/password-reset/confirm")
	@PostMapping("/api/users/login/google")
	
	
	/*
	 * ---------- Authentication APIs ----------
	 * api/auth/register -> post -> register new user -> public (no role required)
	 * api/auth/login -> post -> login user and return authentication token -> public (no role required)
	 * api/auth/logout -> post -> invalidate token and logout -> user, admin, technician
	 * api/auth/password-reset/request -> post -> request password reset link or token to user -> public (no role required)
	 * api/auth/password-reset/confirm -> post -> confirm and set new password -> public (no role required)
	 * api/users/login/google -> post -> login user via google OAuth -> public (no role required)
	 * 
	 * ---------- User Profile Management APIs ---------- 
	 * api/users/all -> get -> fetch all users -> to admin
	 * api/users/{id}/repair-status -> get -> check status for repair orders linked to specific user -> user
	 * api/users/profile -> get -> fetch logged in users' profile -> give specific access according to the role
	 * 
	 * api/users/{id} -> put -> update order detail for user -> admin and technician
	 * api/users/{id}/update -> put -> updates user details such as name, phone, or email for logged-in user -> user and admin
	 * api/users/{id}/change-password -> put -> change password for logged-in user -> user
	 * 
	 * api/users/{id} -> delete -> delete a user by id -> accessible to admin
	 * api/users/{id}/delete -> delete -> logged-in user can delete his/her own account -> user ( soft delete for safety ) and admin
	 * 
	 * ---------- Role Profile Management APIs ----------
	 * api/roles -> get -> fetch a list of all roles -> admin only
	 * api/roles/create -> post -> allow admin to create new role -> admin only
	 * api/roles/{roleId}/update -> put -> allow admin to update an existing role -> admin only
	 * api/roles/{roleId}/delete -> delete -> allow admin to delete role -> admin only
	 * 
	 * ---------- Repair Order Management APIs ----------
	 * api/repairs/all -> get -> fetch all repair jobs -> admin and technician 
	 * api/repairs/{id}/update-status -> get -> update repair job details -> admin and technician
	 * 
	 * api/repairs/assign -> post -> assign repair job to technician -> accessible by admin
	 * api/repairs/create -> post -> create new repair order -> admin and user
	 * 
	 * api/repairs/complete/{id} -> put -> mark repair job as complete -> accessible to technician
	 * 
	 * api/repairs/{id}/cancel -> delete -> cancel repair order -> admin and user
	 * 
	 * ---------- Inventory Management APIs ----------
	 * api/inventory/all -> get -> fetch all inventory items -> admin and technician
	 * api/inventory/{id} -> get -> fetch details of specific inventory items -> admin and technician
	 * 
	 * api/inventory/add -> post -> add a new item to the inventory -> admin only
	 * 
	 * api/inventory/{id}/update -> put -> update details of an inventory item -> admin only
	 * 
	 * api/inventory/{id}/delete -> delete -> remove an inventory item -> admin only
	 * 
	 * ---------- Payment Management APIs ----------
	 * api/payments/all -> get -> fetch all payment records -> admin only
	 * api/payments/{id} -> get -> fetch payment detail by ID -> admin and user
	 * 
	 * api/payments/add -> post -> create a new payment record -> admin and user
	 * api/payments/{id}/refund -> post -> process payment for refund -> admin only
	 * 
	 * api/payments/{id}/update -> put -> update payment details -> admin only
	 * 
	 * ---------- Notification APIs ----------
	 * api/notifications/all -> get -> fetch all notifications for a user -> user
	 * api/notifications/unread -> get -> fetch unread notifications for a user -> user
	 * 
	 * api/notifications/send -> post -> send a notification to a user -> admin only
	 * 
	 * api/notifications/{id}/mark-read -> put -> mark a notification as read -> user
	 * 
	 * ---------- Admin Dashboard APIs ----------
	 * api/reports/generate -> get -> generate reports -> admin only
	 * api/reports/{id} -> get -> fetch specific report details -> admin only
	 * api/dashboard -> get -> view dashboard statistics -> admin and technician
	 * api/settings -> get -> view application settings -> admin only
	 * api/settings -> put -> update application settings -> admin only
	 * 
	 */
}
