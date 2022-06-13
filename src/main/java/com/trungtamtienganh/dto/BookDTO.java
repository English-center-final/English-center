package com.trungtamtienganh.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.trungtamtienganh.entity.NameSlugOnly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

	private Integer id;
	@NotBlank
	@Size(max = 200)
	private String name;
	@JsonProperty(access = Access.READ_ONLY)
	private String image;
	@JsonProperty(access = Access.READ_ONLY)
	private List<NameSlugOnly> exams;

}
