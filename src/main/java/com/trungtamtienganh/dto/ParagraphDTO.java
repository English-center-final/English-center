package com.trungtamtienganh.dto;

import javax.validation.constraints.NotBlank;

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
public class ParagraphDTO {

	private int id;
	@JsonProperty(access = Access.READ_ONLY)
	private String image;
	@NotBlank
	private String content;
	@NotBlank
	private String transcript;
}
