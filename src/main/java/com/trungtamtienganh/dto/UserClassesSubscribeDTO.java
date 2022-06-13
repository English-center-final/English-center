package com.trungtamtienganh.dto;

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
public class UserClassesSubscribeDTO {

	private Integer classesId;
	private String status;
	
	@JsonProperty(access = Access.READ_ONLY)
	private String levelName;
	
	@JsonProperty(access = Access.READ_ONLY)
	private String dateStart;
	
	@JsonProperty(access = Access.READ_ONLY)
	private String date;
	
	@JsonProperty(access = Access.READ_ONLY)
	private String session;
}
