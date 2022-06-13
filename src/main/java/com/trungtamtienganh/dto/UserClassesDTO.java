package com.trungtamtienganh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserClassesDTO {

	private ClassesDTO classes;
	private String status;
	private Integer userId;
	private String email;
	private String name;
	private String phoneNumber;

}
