package com.trungtamtienganh.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamtienganh.dto.ErrorDTO;
import com.trungtamtienganh.dto.WordNoteCategoryDTO;
import com.trungtamtienganh.dto.WordNoteCategorySummaryDTO;
import com.trungtamtienganh.dto.WordNoteDTO;
import com.trungtamtienganh.dto.WordReviewDTO;
import com.trungtamtienganh.service.WordNoteCategoryService;
import com.trungtamtienganh.utils.RestConstant;

@RestController
@RequestMapping("/user/word-note-categories")
@CrossOrigin
public class WordNoteCateogoryController {

	@Autowired
	private WordNoteCategoryService wordNoteCategoryService;

	@GetMapping("")
	public List<WordNoteCategorySummaryDTO> getAllInfos() {

		return wordNoteCategoryService.getAllCategorySummaries();
	}

	@PostMapping(value = "", consumes = RestConstant.CONSUMES_JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public WordNoteCategorySummaryDTO createWordNoteCategory(
			@Valid @RequestBody WordNoteCategorySummaryDTO wordNoteCategory) {

		return wordNoteCategoryService.add(wordNoteCategory.getName());
	}

	@PutMapping(value = "/{id}", consumes = RestConstant.CONSUMES_JSON)
	public WordNoteCategorySummaryDTO updateWordNoteCategory(@PathVariable("id") Integer id,
			@Valid @RequestBody WordNoteCategorySummaryDTO wordNoteCategory) {

		return wordNoteCategoryService.update(id, wordNoteCategory.getName());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteWordNoteCategory(@PathVariable("id") Integer id) {

		wordNoteCategoryService.delete(id);
	}

	@DeleteMapping("/{id}/words/{wordId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteWord(@PathVariable("id") Integer id, @PathVariable("wordId") Integer wordId) {
		wordNoteCategoryService.deleteWord(id, wordId);

	}

	@PostMapping(value = "/add-word", consumes = RestConstant.CONSUMES_JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void addWordNote(@Valid @RequestBody WordNoteDTO wordNoteRequest) {

		Integer wordNoteCategoryId = wordNoteRequest.getWordNoteCategoryId();
		Integer wordId = wordNoteRequest.getWordId();

		wordNoteCategoryService.addWord(wordNoteCategoryId, wordId);
	}

	@GetMapping("/{id}")
	public WordNoteCategoryDTO getById(@PathVariable("id") Integer id) {

		return wordNoteCategoryService.getById(id);
	}

	@GetMapping("/review/{id}")
	public ResponseEntity<?> reviewWordNote(@PathVariable("id") Integer id,
			@RequestParam(value = "type", required = false, defaultValue = "0") int type,
			@RequestParam("ids") List<Integer> ids

	) {

		WordReviewDTO result = wordNoteCategoryService.getWordReview(id, type, ids);

		if (result == null)
			return new ResponseEntity<>(new ErrorDTO(400, "Không còn từ ôn tập"), HttpStatus.OK);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}