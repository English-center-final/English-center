package com.trungtamtienganh.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.trungtamtienganh.validator.Result;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO extends QuestionSummaryDTO {

	@JsonProperty(access = Access.READ_ONLY)
	private int type;
	@NotBlank
	@Size(max = 1)
	@Getter(AccessLevel.NONE)
	@Result
	private String result;
	private String extra;
	@JsonProperty(access = Access.READ_ONLY)
	private String audio;
	
	public String getResult() {

		if (type == 2 && result.equals("d"))
			return "a";

		return result;
	}

}