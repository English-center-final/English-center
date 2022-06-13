package com.trungtamtienganh.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

	@NotBlank
	@Size(max = 100)
	private String name;
	@NotBlank
	@Size(max = 20)
	private String username;
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank
	@Size(max = 20)
	private String password;

}