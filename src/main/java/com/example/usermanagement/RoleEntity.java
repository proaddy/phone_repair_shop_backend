package com.example.usermanagement;

import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//@Embeddable
@Entity
@Table(name = "roles")
public class RoleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment, Primary Key
	@Column(name = "role_id")
	private long roleId;
	
	@NotBlank(message = "role name is required")
	@Size(min = 3, message = "Role name eg. admin, user, technician")
	@Column(nullable = false, unique = true, length = 50)
	private String name;
	
	@Column(length = 255)
	private String description;

	// ---------- GETTER and SETTER ----------

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

/*
 * create table roles (
 * id bigint primary key,
 * name varchar(50) not null unique,
 * description varchar(255);
 */