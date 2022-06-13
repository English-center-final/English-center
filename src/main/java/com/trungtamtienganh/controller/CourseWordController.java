package com.trungtamtienganh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.WordDTO;
import com.trungtamtienganh.service.CourseWordService;

@RestController
@RequestMapping("/course-words")
@CrossOrigin
public class CourseWordController {

	@Autowired
	private CourseWordService courseWordService;

	@GetMapping("")
	public PaginationWrapper<List<WordDTO>> getWords(@RequestParam("courseSlug") String courseSlug,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "30") int size) {

		return courseWordService.getList(courseSlug, page, size);
	}
}
