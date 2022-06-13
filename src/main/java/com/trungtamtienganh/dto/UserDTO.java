package com.trungtamtienganh.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.trungtamtienganh.utils.MyRegex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Integer id;
	
	@NotBlank
	@Size(max = 50)
	@Pattern(regexp = MyRegex.USERNAME_REGEX, message = "Invalid")
	private String name;
	
	@NotBlank
	@Email
	private String username;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Pattern(regexp = MyRegex.PASSWORD_REGEX,  message = "Invalid")
	private String password;
	
	@NotBlank
	@Pattern(regexp = MyRegex.PHONE_REGEX , message = "Invalid")
	private String phoneNumber;
	
	private String image;
	
	private boolean gender;
	
	@JsonProperty(access = Access.READ_ONLY)
	private boolean actived;
	
	@JsonProperty(access = Access.READ_ONLY)
	private List<String> roles;
}
