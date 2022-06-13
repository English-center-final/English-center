package com.trungtamtienganh.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.trungtamtienganh.validator.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO {

	private Integer id;
	@NotBlank
	@Size(max = 200)
	private String name;
	@JsonProperty(access = Access.READ_ONLY)
	private String slug;
	@JsonProperty(access = Access.READ_ONLY)
	private String part1Audio;
	@JsonProperty(access = Access.READ_ONLY)
	private String part2Audio;
	@JsonProperty(access = Access.READ_ONLY)
	private String part3Audio;
	@JsonProperty(access = Access.READ_ONLY)
	private String part4Audio;
	@Id
	private Integer bookId;
	private String bookName;
}