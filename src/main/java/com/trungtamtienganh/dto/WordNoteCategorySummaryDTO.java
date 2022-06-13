package com.trungtamtienganh.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WordNoteCategorySummaryDTO {

	private Integer id;
	@NotBlank
	@Size(max = 100)
	private String name;
	@JsonProperty(access = Access.READ_ONLY)
	private String createDate;
	@JsonProperty(access = Access.READ_ONLY)
	private int wordNumber;

}