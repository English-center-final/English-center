package com.trungtamtienganh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.converter.CourseConverter;
import com.trungtamtienganh.dto.CourseSummaryDTO;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.entity.Course;
import com.trungtamtienganh.entity.CourseWord;
import com.trungtamtienganh.entity.CourseWord_PK;
import com.trungtamtienganh.entity.Word;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.CourseRepository;
import com.trungtamtienganh.repository.CourseWordRepository;
import com.trungtamtienganh.repository.TopicRepository;
import com.trungtamtienganh.repository.WordRepository;
import com.trungtamtienganh.service.AwsS3Service;
import com.trungtamtienganh.service.CourseService;
import com.trungtamtienganh.utils.MyConstant;


@Service
@Transactional
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private CourseConverter courseConverter;

	@Autowired
	private WordRepository wordRepository;

	@Autowired
	private CourseWordRepository courseWordRepository;
	
	@Autowired
	private AwsS3Service awsS3Service;

	@Override
	public PaginationWrapper<List<CourseSummaryDTO>> getCourseInfos(String name, String topicSlug, int page, int size) {

		if (name == null || topicSlug == null || page < 0 || size < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Page<Course> coursePage = courseRepository.findAllByNameContainingAndTopicSlugContaining(name, topicSlug,
				PageRequest.of(page, size));

		PaginationWrapper<List<CourseSummaryDTO>> result = new PaginationWrapper<>();
		result.setPage(page);
		result.setSize(size);
		result.setTotalPages(coursePage.getTotalPages());

		List<CourseSummaryDTO> data = coursePage.toList().stream().map(c -> courseConverter.toCourseInfoRequest(c))
				.collect(Collectors.toList());
		result.setData(data);

		return result;
	}

	@Override
	public CourseSummaryDTO getBySlug(String slug) {

		if (slug == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Course course = courseRepository.findBySlug(slug)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.COURSE));

		return courseConverter.toCourseInfoRequest(course);

	}

	@Override
	public CourseSummaryDTO save(CourseSummaryDTO courseSummaryDTO) {

		validate(courseSummaryDTO);

		Course course = courseRepository.save(courseConverter.toCourse(courseSummaryDTO));

		return courseConverter.toCourseInfoRequest(course);
	}

	private void validate(CourseSummaryDTO courseSummaryDTO) {

		if (courseSummaryDTO == null || courseSummaryDTO.getId() < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Integer courseId = courseSummaryDTO.getId();

		if (courseId != 0 && !courseRepository.existsById(courseId))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.COURSE);

		if (courseRepository.existsByIdNotAndName(courseId, courseSummaryDTO.getName())) {

			Map<String, String> error = new HashMap<>();
			error.put("name", "Tên Course bị trùng");

			throw MyExceptionHelper.throwRuntimeCustomException(error);
		}

		if (!topicRepository.existsById(courseSummaryDTO.getTopicId()))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.TOPIC);

	}

	@Override
	public void delete(Integer id) {

		if (id == null || id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Course course = courseRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.COURSE));

		String image = course.getImage();

		courseRepository.deleteById(id);

		if (image != null) {
			awsS3Service.deleteObjectFromUrl(image);
		}

	}

	@Override
	public String uploadImage(Integer id, MultipartFile image) {

		if (id == null || image == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Course course = courseRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.COURSE));

		String imageString = course.getImage();
		if (imageString != null) {

			awsS3Service.deleteObjectFromUrl(imageString);
		}

		String blogImage = awsS3Service.uploadObject(image);
		course.setImage(blogImage);

		courseRepository.save(course);

		return blogImage;
	}

	@Override
	public void addWord(Integer id, Integer wordId) {

		checkCourseWordId(id, wordId);

		CourseWord_PK courseWord_PK = new CourseWord_PK(id, wordId);

		if (courseWordRepository.existsById(courseWord_PK))
			return;

		CourseWord courseWord = new CourseWord(new Course(id), new Word(wordId));

		courseWordRepository.save(courseWord);
	}

	@Override
	public void deleteWord(Integer id, Integer wordId) {

		checkCourseWordId(id, wordId);

		CourseWord_PK courseWord_PK = new CourseWord_PK(id, wordId);

		if (!courseWordRepository.existsById(courseWord_PK))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.COURSE_WORD);

		courseWordRepository.deleteById(courseWord_PK);
	}

	private void checkCourseWordId(Integer courseId, Integer wordId) {

		if (courseId == null || wordId == null || courseId <= 0 || wordId <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		if (!courseRepository.existsById(courseId))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.COURSE);

		if (!wordRepository.existsById(wordId))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.WORD);

	}
}