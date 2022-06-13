package com.trungtamtienganh.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.trungtamtienganh.converter.WordConverter;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.WordDTO;
import com.trungtamtienganh.entity.CourseWord;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.CourseRepository;
import com.trungtamtienganh.repository.CourseWordRepository;
import com.trungtamtienganh.service.CourseWordService;
import com.trungtamtienganh.utils.MyConstant;

@Service
@Transactional
public class CourseWordServiceImpl implements CourseWordService {

	@Autowired
	private CourseWordRepository courseWordRepository;
	@Autowired
	private WordConverter wordConverter;
	@Autowired
	private CourseRepository courseRepository;

	@Override
	public PaginationWrapper<List<WordDTO>> getList(String courseSlug, int page, int size) {

		if (courseSlug == null || page < 0 || size < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		if (!courseRepository.existsBySlug(courseSlug))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.COURSE);

		PaginationWrapper<List<WordDTO>> result = new PaginationWrapper<>();

		result.setPage(page);
		result.setSize(size);

		Page<CourseWord> courseWordPage = courseWordRepository.findAllByCourseSlug(courseSlug,
				PageRequest.of(page, size));
		result.setTotalPages(courseWordPage.getTotalPages());

		List<WordDTO> wordDTOs = courseWordPage.toList().stream()
				.map(courseWordEle -> wordConverter.toWordDTO(courseWordEle.getWord())).collect(Collectors.toList());

		result.setData(wordDTOs);

		return result;
	}

}