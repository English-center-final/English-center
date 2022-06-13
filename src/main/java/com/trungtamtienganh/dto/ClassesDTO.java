package com.trungtamtienganh.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.trungtamtienganh.validator.ClassStatus;
import com.trungtamtienganh.validator.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassesDTO {
	private Integer id;

	@NotNull
	private Integer amount;
	
	@NotBlank
//	@DateStart
	private String dateStart;

	@NotNull
	private Integer numOfLessons;

	@ClassStatus
	private String status;
	@NotBlank
	private String description;
	@NotBlank
	private String date;

	@Id
	private Integer levelId;

	@JsonProperty(access = Access.READ_ONLY)
	private String levelName;
	
	@Id
	private Integer branchId;
	
	@JsonProperty(access = Access.READ_ONLY)
	private String branchName;
	
	@JsonProperty(access = Access.READ_ONLY)
	private String branchAddress;

	@NotBlank
	private String room;
	
	@NotBlank
	private String session;
	
	@JsonProperty(access = Access.READ_ONLY)
	private Integer numOfRegister;
}
