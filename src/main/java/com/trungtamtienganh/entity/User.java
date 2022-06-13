package com.trungtamtienganh.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.trungtamtienganh.utils.RoleConstant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String username;
	private String phoneNumber;
	private String password;
	private String image;
	private boolean gender;
	private boolean actived;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserRole> roles = new ArrayList<>();

	public User(Integer id) {
		super();
		this.id = id;
	}
	
	public User(String name, String username, String password, String phoneNumber, boolean gender,
			List<UserRole> roles) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.roles = roles;
	}


	public List<String> getRolesString() {
		return this.roles.stream().map(userRole -> userRole.getRole().getName()).collect(Collectors.toList());
	}

	public boolean isAdmin() {

		for (UserRole userRole : roles) {

			if (userRole.getRole().getName().equals(RoleConstant.ROLE_ADMIN))
				return true;
		}

		return false;
	}

}