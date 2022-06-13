package com.trungtamtienganh.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.dto.CourseSummaryDTO;
import com.trungtamtienganh.dto.CourseWordDTO;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.service.CourseService;
import com.trungtamtienganh.utils.MyConstant;
import com.trungtamtienganh.utils.RestConstant;

@RestController
@RequestMapping("/admin/courses")
@CrossOrigin
public class CourseAdminController {

	@Autowired
	private CourseService courseService;

	@PostMapping(value = "", consumes = RestConstant.CONSUMES_JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public CourseSummaryDTO addCourse(@Valid @RequestBody CourseSummaryDTO courseSummaryDTO) {

		courseSummaryDTO.setId(0);
		return courseService.save(courseSummaryDTO);
	}

	@PutMapping(value = "/{id}", consumes = RestConstant.CONSUMES_JSON)
	public CourseSummaryDTO updateCourse(@PathVariable("id") Integer id,
			@Valid @RequestBody CourseSummaryDTO courseSummaryDTO) {

		if (id <= 0)
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.COURSE);

		courseSummaryDTO.setId(id);
		return courseService.save(courseSummaryDTO);
	}

	@PutMapping(value = "/{id}/image")
	public String updateImage(@PathVariable("id") Integer id, @RequestParam("image") MultipartFile image) {

		String fileName = courseService.uploadImage(id, image);
		return fileName;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteCourse(@PathVariable("id") Integer id) {

		courseService.delete(id);
	}

	@PostMapping(value = "/{id}/course-word")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void addWord(@PathVariable("id") Integer id, @Valid @RequestBody CourseWordDTO courseWordDTO) {

		courseService.addWord(id, courseWordDTO.getWordId());
	}

	@DeleteMapping(value = "/{courseId}/course-word/{wordId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteWord(@PathVariable("courseId") Integer courseId, @PathVariable("wordId") Integer wordId) {

		courseService.deleteWord(courseId, wordId);
	}
}