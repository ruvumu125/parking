package com.parking.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity {

	@Column(nullable = false)
	private String userFullName;

	@Column(name = "useremail")
	private String userEmail;

	@Column(name = "userpassword")
	private String userPassword;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "userrole")
	private UserRoleEnum userRoleEnum;

	@Column(name = "userphonenumber")
	private String userPhoneNumber;

	@Column(name = "isuseractive")
	private Boolean isUserActive;

	@OneToMany(mappedBy = "user")
	private List<Superadmin> superadmins;

	@OneToMany(mappedBy = "user")
	private List<Admin> admins;

	@OneToMany(mappedBy = "user")
	private List<Agent> agents;
}
