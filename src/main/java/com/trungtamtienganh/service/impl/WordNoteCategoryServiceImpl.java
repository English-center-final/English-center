package com.trungtamtienganh.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trungtamtienganh.converter.WordNoteCategoryConverter;
import com.trungtamtienganh.dto.WordNoteCategoryDTO;
import com.trungtamtienganh.dto.WordNoteCategorySummaryDTO;
import com.trungtamtienganh.dto.WordReviewDTO;
import com.trungtamtienganh.entity.Word;
import com.trungtamtienganh.entity.WordNote;
import com.trungtamtienganh.entity.WordNoteCategory;
import com.trungtamtienganh.entity.WordNote_PK;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.WordNoteCategoryRepository;
import com.trungtamtienganh.repository.WordNoteRepository;
import com.trungtamtienganh.repository.WordRepository;
import com.trungtamtienganh.service.WordNoteCategoryService;
import com.trungtamtienganh.utils.AuthenInfo;
import com.trungtamtienganh.utils.CommonFuc;
import com.trungtamtienganh.utils.MyConstant;
import com.trungtamtienganh.utils.WordNoteCategoryHandler;

@Service
@Transactional
public class WordNoteCategoryServiceImpl implements WordNoteCategoryService {

	@Autowired
	private WordNoteCategoryRepository wordNoteCategoryRepository;
	@Autowired
	private WordNoteCategoryConverter wordNoteCategoryConverter;
	@Autowired
	private WordNoteRepository wordNoteRepository;
	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private AuthenInfo authenInfo;

	@Override
	public List<WordNoteCategorySummaryDTO> getAllCategorySummaries() {

		AuthenInfo.checkLogin();

		String username = AuthenInfo.getUsername();

		return wordNoteCategoryRepository.findAllByUserUsername(username).stream()
				.map(wnc -> wordNoteCategoryConverter.toWordNoteCategoryDTO(wnc)).collect(Collectors.toList());
	}

	@Override
	public WordNoteCategorySummaryDTO add(String name) {

		AuthenInfo.checkLogin();

		if (name == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		WordNoteCategory wordNoteCategory = new WordNoteCategory();
		wordNoteCategory.setName(name);
		wordNoteCategory.setCreateDate(LocalDate.now());
		wordNoteCategory.setUser(authenInfo.getUser());

		return wordNoteCategoryConverter.toWordNoteCategoryDTO(wordNoteCategoryRepository.save(wordNoteCategory));
	}

	@Override
	public WordNoteCategorySummaryDTO update(Integer id, String name) {

		AuthenInfo.checkLogin();

		if (id == null || name == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		checkAuthenticationForCategory(id);

		WordNoteCategory result = wordNoteCategoryRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.NOTE));
		result.setName(name);

		return wordNoteCategoryConverter.toWordNoteCategoryDTO(wordNoteCategoryRepository.save(result));
	}

	@Override
	public void delete(Integer id) {

		AuthenInfo.checkLogin();

		if (id == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		checkAuthenticationForCategory(id);

		wordNoteCategoryRepository.deleteById(id);
	}

	@Override
	public void addWord(Integer id, Integer wordId) {

		AuthenInfo.checkLogin();

		if (id == null || wordId == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		// check có quyền với category
		checkAuthenticationForCategory(id);

		// check có từ vựng đó không
		if (!wordRepository.existsById(wordId))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.WORD);

		// check từ này đã lưu chưa
		WordNote_PK wordNote_PK = new WordNote_PK(id, wordId);
		// nếu tồn tài rồi thì không lưu nữa
		if (wordNoteRepository.existsById(wordNote_PK))
			return;

		WordNote wordNote = new WordNote(new WordNoteCategory(id), new Word(wordId));
		wordNoteRepository.save(wordNote);
	}

	@Override
	public WordNoteCategoryDTO getById(Integer id) {

		if (id == null || id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		checkAuthenticationForCategory(id);

		WordNoteCategory wordNoteCategory = wordNoteCategoryRepository.findById(id).get();

		return wordNoteCategoryConverter.toWordNoteCategoryDTO(wordNoteCategory);
	}

	@Override
	public WordReviewDTO getWordReview(Integer id, int type, List<Integer> idsWasReview) {

		if (id == null || (type != 0 && type != 1) || idsWasReview == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		checkAuthenticationForCategory(id);

		WordNoteCategory wordNoteCategory = wordNoteCategoryRepository.findById(id).get();

		// lọc ra từ chưa review
		List<WordNote> wordNotesNotReview = wordNoteCategory.getWords().stream().filter(wordNoteCategoryEle -> {

			Integer idTempt = wordNoteCategoryEle.getWord().getId();

			if (idsWasReview.contains(idTempt))
				return false;

			return true;
		}).collect(Collectors.toList());

		int sizeOfWordNotesNotReview = wordNotesNotReview.size();

		if (sizeOfWordNotesNotReview == 0)
			return null;

		// ramdom lấy ra 1 từ
		Random rand = new Random();
		int indexRandom = rand.nextInt(sizeOfWordNotesNotReview);
		WordNote wordNoteReview = wordNotesNotReview.get(indexRandom);

		// random suggestion
		List<String> suggestionsRandom;

		if (type == 0)
			suggestionsRandom = WordNoteCategoryHandler.randomSuggestions(wordNoteCategory, wordNoteReview.getWord());
		else
			suggestionsRandom = CommonFuc.shuffleOrder(wordNoteReview.getWord().getName());

		return wordNoteCategoryConverter.toWordReviewDTO(wordNoteReview, suggestionsRandom);
	}

	@Override
	public void deleteWord(Integer id, Integer wordId) {

		if (id == null || wordId == null || id <= 0 || wordId <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		checkAuthenticationForCategory(id);

		WordNote_PK wordNote_PK = new WordNote_PK(id, wordId);

		if (wordNoteRepository.existsById(wordNote_PK))
			wordNoteRepository.deleteById(wordNote_PK);

	}

	// check xem user có vai trò với wordNoteCategory
	private void checkAuthenticationForCategory(Integer id) {

		if (!wordNoteCategoryRepository.existsByUserUsernameAndId(AuthenInfo.getUsername(), id))
			throw MyExceptionHelper.throwAuthenticationException();
	}

}