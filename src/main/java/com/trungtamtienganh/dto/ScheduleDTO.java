package com.trungtamtienganh.dto;

import javax.validation.constraints.NotBlank;

import com.trungtamtienganh.validator.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
	
	private Integer id;

	@NotBlank
	private String date;
	@NotBlank
	private String description;
	@NotBlank
	private String room;
	@NotBlank
	private String session;

	private boolean status;

	@Id
	private Integer classId;
}
