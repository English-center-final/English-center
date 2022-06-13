package com.trungtamtienganh.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.trungtamtienganh.validator.TopicIdExists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSummaryDTO {

	private int id;
	@NotBlank
	@Size(max = 200)
	private String name;
	@JsonProperty(access = Access.READ_ONLY)
	private String slug;
	@JsonProperty(access = Access.READ_ONLY)
	private String image;
	@NotBlank
	@Size(max = 500)
	private String description;
	@JsonProperty(access = Access.READ_ONLY)
	private int wordNumber;
	@JsonProperty(access = Access.READ_ONLY)
	private int personNumber;
	@TopicIdExists
	private Integer topicId;

}