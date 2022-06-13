package com.trungtamtienganh.converter;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trungtamtienganh.dto.WordDTO;
import com.trungtamtienganh.dto.WordNoteCategoryDTO;
import com.trungtamtienganh.dto.WordNoteCategorySummaryDTO;
import com.trungtamtienganh.dto.WordReviewDTO;
import com.trungtamtienganh.entity.Word;
import com.trungtamtienganh.entity.WordNote;
import com.trungtamtienganh.entity.WordNoteCategory;
import com.trungtamtienganh.utils.DateProcessor;

@Component
public class WordNoteCategoryConverter {

	@Autowired
	private WordConverter wordConverter;

	public WordNoteCategorySummaryDTO toWordNoteCategorySummaryDTO(WordNoteCategory wordNoteCategory) {

		WordNoteCategorySummaryDTO result = new WordNoteCategorySummaryDTO();
		result.setId(wordNoteCategory.getId());
		result.setName(wordNoteCategory.getName());

		String createDateString = DateProcessor.toDateString(wordNoteCategory.getCreateDate());
		result.setCreateDate(createDateString);

		result.setWordNumber(wordNoteCategory.getWords().size());

		return result;
	}

	public WordReviewDTO toWordReviewDTO(WordNote wordNote, List<String> questions) {
		WordReviewDTO result = new WordReviewDTO();

		Word word = wordNote.getWord();

		result.setId(word.getId());
		result.setName(word.getName());
		result.setImage(word.getImage());
		result.setDefinition(word.getDefinition());
		result.setSuggestions(questions);

		return result;
	}

	public WordNoteCategoryDTO toWordNoteCategoryDTO(WordNoteCategory wordNoteCategory) {

		WordNoteCategoryDTO result = new WordNoteCategoryDTO();

		result.setId(wordNoteCategory.getId());
		result.setName(wordNoteCategory.getName());

		String createDateString = DateProcessor.toDateString(wordNoteCategory.getCreateDate());
		result.setCreateDate(createDateString);

		result.setWordNumber(wordNoteCategory.getWords().size());

		List<WordDTO> wordDTOs = wordNoteCategory.getWords().stream()
				.map(wordNoteEle -> wordConverter.toWordDTO(wordNoteEle.getWord())).collect(Collectors.toList());
		result.setWords(wordDTOs);

		return result;
	}

}