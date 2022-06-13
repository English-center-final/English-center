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
public class QuestionSummaryDTO {

	private int id;
	@JsonProperty(access = Access.READ_ONLY)
	private int stt;
	@NotBlank
	@Size(max = 255)
	private String content;
	@NotBlank
	@Size(max = 255)
	private String a;
	@NotBlank
	@Size(max = 255)
	private String b;
	@NotBlank
	@Size(max = 255)
	private String c;
	@NotBlank
	@Size(max = 255)
	private String d;
}
