package com.trungtamtienganh.dto;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseWordDTO {

	private Integer courseId;
	@NotNull
	private Integer wordId;
}