package com.trungtamtienganh.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordNoteDTO {

	@NotNull(message = "wordNoteCategoryId không null")
	@Min(value = 1, message = "wordNoteCategoryId >= 1")
	private Integer wordNoteCategoryId;
	@NotNull(message = "wordId không null")
	@Min(value = 1, message = "wordId >= 1")
	private Integer wordId;
}