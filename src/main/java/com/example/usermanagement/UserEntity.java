package com.example.usermanagement;

import jakarta.persistence.Column;
//import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment, Primary Key
	@Column(name = "id")
	private long userId;
	
	/*
	 * using annotation is for application level validation
	 * but writing nullable in the column in database level validation
	 */
	@NotBlank(message = "Username is required")
	@Size(min = 3, max = 50, message = "length of username must be between 3 and 50 characters")
	@Column(nullable = false, unique = true, length = 50)
	private String username;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@NotBlank(message = "password is required")
	@Size(min = 8, message = "Password must atleat be 8 characters")
	@Column(nullable = false)
	private String password;
	
//	@Size(max = 15, message = "phone number should be less than 15 characters")
//	@Column(length = 15, nullable = true)
	@Pattern(regexp = "\\d{10,15}", message = "Phone number must be numeric and between 10-15 digits")
	@Column(length = 15)
	private String phone_number;
	
	/*
	 * Lazy loading is delaying the loading until it's needed
	 * Eager loading is performing an action as soon as it's encountered
	 */
//	@Embedded
	@ManyToOne(fetch = FetchType.EAGER) // Foreign Key
	@JoinColumn(name = "role_id", nullable = false)
	private RoleEntity role;
	
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status = UserStatus.ACTIVE; // Default is ACTIVE
	
	@Column(nullable = false)
    private LocalDateTime created_at;
    
    @Column(nullable = false)
    private LocalDateTime updated_at;
    
    @PrePersist // runs before the entity is first saved to the database.
	public void onCreate() {
		this.created_at = LocalDateTime.now();
		this.updated_at = LocalDateTime.now();
	}
	
    @PreUpdate // JPA life event that's triggered when associated entity's database is updated
	public void onUpdate() {
		this.updated_at = LocalDateTime.now();
	}
    
    public enum UserStatus {
        ACTIVE,
        INACTIVE,
        BANNED
    }
    
	// ---------- GETTER and SETTER ----------

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
}

/*
 * id int primary key,
 * username varchar(50) not null unique,
 * email varchar(100) not null unique,
 * password varchar(255) not null,
 * phone_number varchar(15),
 * role_id bigint,
 * created_at datetime,
 * updated_at datetime,
 * status ENUM('ACTIVE','INACTIVE','BANNED') not null
 */
