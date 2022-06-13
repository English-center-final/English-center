package com.trungtamtienganh.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trungtamtienganh.dto.CourseDTO;
import com.trungtamtienganh.dto.CourseSummaryDTO;
import com.trungtamtienganh.dto.WordDTO;
import com.trungtamtienganh.entity.Course;
import com.trungtamtienganh.entity.Topic;
import com.trungtamtienganh.repository.CourseRepository;
import com.trungtamtienganh.utils.CommonFuc;


@Component
public class CourseConverter {

	@Autowired
	private WordConverter wordConverter;

	@Autowired
	private CourseRepository courseRepository;

	public CourseSummaryDTO toCourseInfoRequest(Course course) {

		CourseSummaryDTO result = new CourseSummaryDTO();
		result.setId(course.getId());
		result.setName(course.getName());
		result.setSlug(course.getSlug());
		result.setImage(course.getImage());
		result.setDescription(course.getDescription());
		result.setWordNumber(course.getWords().size());
		result.setTopicId(course.getTopic().getId());

		return result;
	}

	public CourseDTO toCourseRequest(Course course) {

		CourseDTO result = new CourseDTO();
		result.setId(course.getId());
		result.setName(course.getName());
		result.setSlug(course.getSlug());
		result.setImage(course.getImage());
		result.setDescription(course.getDescription());
		result.setWordNumber(course.getWords().size());

		List<WordDTO> wordDTOs = new ArrayList<>();
		course.getWords().forEach(courseWord -> {

			WordDTO wordDTO = wordConverter.toWordDTO(courseWord.getWord());
			wordDTOs.add(wordDTO);
		});
		result.setWords(wordDTOs);

		return result;
	}

	public Course toCourse(CourseSummaryDTO courseSummaryDTO) {

		Integer id = courseSummaryDTO.getId();

		Course courseResult = courseRepository.findById(id).orElse(new Course(0));

		String name = courseSummaryDTO.getName();
		courseResult.setName(name);
		courseResult.setSlug(CommonFuc.toSlug(name));

		courseResult.setDescription(courseSummaryDTO.getDescription());
		courseResult.setTopic(new Topic(courseSummaryDTO.getTopicId()));

		return courseResult;
	}

}