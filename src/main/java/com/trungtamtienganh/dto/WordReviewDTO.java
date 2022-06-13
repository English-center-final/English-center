package com.trungtamtienganh.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordReviewDTO {

	private Integer id;
	private String name;
	private String image;
	private String definition;

	private List<String> suggestions;

}