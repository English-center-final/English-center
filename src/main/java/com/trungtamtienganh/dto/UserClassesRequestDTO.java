package com.trungtamtienganh.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.trungtamtienganh.validator.UserClassesStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserClassesRequestDTO {

	@NotNull
	private Integer classesId;
	
	@NotBlank
	@UserClassesStatus
	private String status;
	
	private Integer userId;

}
