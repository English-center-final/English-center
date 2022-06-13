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
public class WordDTO {

	private Integer id;
	@NotBlank
	@Size(max = 200)
	private String name;
	@NotBlank
	@Size(max = 200)
	private String mean;
	@NotBlank
	@Size(max = 200)
	private String type;
	@NotBlank
	@Size(max = 200)
	private String pronounce;
	@JsonProperty(access = Access.READ_ONLY)
	private String sound;
	@NotBlank
	@Size(max = 500)
	private String definition;
	@NotBlank
	@Size(max = 500)
	private String example;
	@JsonProperty(access = Access.READ_ONLY)
	private String image;

}