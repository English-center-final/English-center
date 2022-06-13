package com.trungtamtienganh.dto;

import com.trungtamtienganh.validator.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusDTO {

	@Id
	private Integer id;
	
	private boolean actived;
}
