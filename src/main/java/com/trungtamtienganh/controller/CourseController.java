package com.trungtamtienganh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamtienganh.dto.CourseSummaryDTO;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.TopicDTO;
import com.trungtamtienganh.service.CourseService;
import com.trungtamtienganh.service.TopicService;


@RestController
@RequestMapping("/courses")
@CrossOrigin
public class CourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private TopicService topicService;

	@GetMapping("")
	public PaginationWrapper<List<CourseSummaryDTO>> getCourseInfos(

			@RequestParam(name = "name", required = false, defaultValue = "") String name,
			@RequestParam(name = "topicSlug", required = false, defaultValue = "") String topicSlug,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "12") int size) {

		return courseService.getCourseInfos(name, topicSlug, page, size);

	}

	@GetMapping("/{slug}")
	public CourseSummaryDTO getCourseBySlug(@PathVariable("slug") String slug) {

		return courseService.getBySlug(slug);
	}

	@GetMapping("/topics")
	public List<TopicDTO> getTopics() {

		return topicService.getAll();
	}
}