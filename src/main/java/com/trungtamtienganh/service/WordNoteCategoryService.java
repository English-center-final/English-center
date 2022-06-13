package com.trungtamtienganh.service;

import java.util.List;

import com.trungtamtienganh.dto.WordNoteCategoryDTO;
import com.trungtamtienganh.dto.WordNoteCategorySummaryDTO;
import com.trungtamtienganh.dto.WordReviewDTO;

public interface WordNoteCategoryService {

	List<WordNoteCategorySummaryDTO> getAllCategorySummaries();

	WordNoteCategorySummaryDTO add(String name);

	WordNoteCategorySummaryDTO update(Integer id, String name);

	void delete(Integer id);

	void addWord(Integer id, Integer wordId);

	WordNoteCategoryDTO getById(Integer id);

	WordReviewDTO getWordReview(Integer slug, int type, List<Integer> idsWasReview);

	void deleteWord(Integer id, Integer wordId);
}