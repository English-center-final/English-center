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
public class LevelDTO {
	private Integer id;

	@NotBlank
	private String name;
	@Size(max = 500)
	private String description;
	private String content;
	@JsonProperty(access = Access.READ_ONLY)
	private String slug;
	@JsonProperty(access = Access.READ_ONLY)
	private String image;
}
