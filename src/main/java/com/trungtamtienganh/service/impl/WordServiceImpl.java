package com.trungtamtienganh.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.converter.WordConverter;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.WordDTO;
import com.trungtamtienganh.entity.Word;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.WordRepository;
import com.trungtamtienganh.service.AwsS3Service;
import com.trungtamtienganh.service.WordService;
import com.trungtamtienganh.utils.MyConstant;

@Service
@Transactional
public class WordServiceImpl implements WordService {

	@Autowired
	private WordRepository wordRepository;

	@Autowired
	private WordConverter wordConverter;

	@Autowired
	private AwsS3Service awsS3Service;

	@Override
	public PaginationWrapper<List<WordDTO>> getList(String name, int page, int size) {

		PaginationWrapper<List<WordDTO>> wordDTOsPageResult = new PaginationWrapper<List<WordDTO>>();
		wordDTOsPageResult.setPage(page);
		wordDTOsPageResult.setSize(size);

		Page<Word> wordsPage = wordRepository.findAllByNameContaining(name, PageRequest.of(page, size));
		List<WordDTO> wordDTOs = wordsPage.toList().stream().map(wordEle -> wordConverter.toWordDTO(wordEle))
				.collect(Collectors.toList());
		wordDTOsPageResult.setData(wordDTOs);

		wordDTOsPageResult.setTotalPages(wordsPage.getTotalPages());

		return wordDTOsPageResult;
	}

	@Override
	public WordDTO save(WordDTO wordDTO) {

		validate(wordDTO);

		Word wordWasSave = wordRepository.save(wordConverter.toWord(wordDTO));

		return wordConverter.toWordDTO(wordWasSave);
	}

	private void validate(WordDTO wordDTO) {
		if (wordDTO == null || wordDTO.getId() == null || wordDTO.getId() < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Integer id = wordDTO.getId();
		if (id != 0 && !wordRepository.existsById(id))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.WORD);

	}

	@Override
	public void delete(Integer id) {

		if (id == null || id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Word word = wordRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.WORD));

		wordRepository.deleteById(id);

		String image = word.getImage();
		if (image != null) {
			awsS3Service.deleteObjectFromUrl(image);
		}

		String sound = word.getSound();
		if (sound != null) {
			awsS3Service.deleteObjectFromUrl(sound);
		}
	}

	@Override
	public String uploadImage(Integer id, MultipartFile image) {

		if (id == null || id <= 0 || image == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Word word = wordRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.WORD));

		String imageString = word.getImage();
		if (imageString != null) {
			awsS3Service.deleteObjectFromUrl(imageString);
		}

		String imageUpload = awsS3Service.uploadObject(image);
		word.setImage(imageUpload);

		wordRepository.save(word);

		return imageUpload;
	}

	@Override
	public String uploadSound(Integer id, MultipartFile sound) {
		if (id == null || id <= 0 || sound == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Word word = wordRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.WORD));

		String soundString = word.getSound();
		if (soundString != null) {
			awsS3Service.deleteObjectFromUrl(soundString);
		}

		String soundUpload = awsS3Service.uploadObject(sound);
		word.setSound(soundUpload);

		wordRepository.save(word);

		return soundUpload;
	}
}