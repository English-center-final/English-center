package com.trungtamtienganh.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.trungtamtienganh.entity.Word;
import com.trungtamtienganh.entity.WordNoteCategory;

public class WordNoteCategoryHandler {

	public static List<String> randomSuggestions(WordNoteCategory wordNoteCategory, Word wordReview) {

		// Lấy các từ mà không có từ đang review
		List<String> wordsTempt = wordNoteCategory.getWords().stream()
				.filter(wordEle -> wordEle.getWord().getId() != wordReview.getId())
				.map(wordEle -> wordEle.getWord().getName()).collect(Collectors.toList());

		List<String> result = new ArrayList<>();
		result.add(wordReview.getName());

		int size = 0;
		while (size < 3 && wordsTempt.size() > 0) {

			Random random = new Random();
			int indexRandom = random.nextInt(wordsTempt.size());
			String wordName = wordsTempt.get(indexRandom);
			result.add(wordName);
			wordsTempt.remove(indexRandom);

			size++;
		}

		return CommonFuc.shuffleOrder(result);
	}

}