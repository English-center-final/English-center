package com.trungtamtienganh.converter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trungtamtienganh.dto.WordDTO;
import com.trungtamtienganh.entity.Word;
import com.trungtamtienganh.repository.WordRepository;


@Component
public class WordConverter {

	@Autowired
	private WordRepository wordRepository;

	public WordDTO toWordDTO(Word word) {

		WordDTO result = new WordDTO();
		result.setId(word.getId());
		result.setName(word.getName());
		result.setMean(word.getMean());
		result.setType(word.getType());
		result.setPronounce(word.getPronounce());
		result.setSound(word.getSound());
		result.setDefinition(word.getDefinition());
		result.setExample(word.getExample());
		result.setImage(word.getImage());

		return result;
	}

	public Word toWord(WordDTO wordDTO) {

		Integer id = wordDTO.getId();

		Word result = wordRepository.findById(id).orElse(new Word(0));

		result.setName(wordDTO.getName());
		result.setMean(wordDTO.getMean());
		result.setType(wordDTO.getType());
		result.setPronounce(wordDTO.getPronounce());
		// result.setSound(wordDTO.getSound());
		result.setDefinition(wordDTO.getDefinition());
		result.setExample(wordDTO.getExample());
		// result.setImage(wordDTO.getImage());

		return result;
	}
}