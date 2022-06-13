package com.trungtamtienganh.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamtienganh.dto.ExamQuestionDTO;
import com.trungtamtienganh.dto.ExamResultDTO;
import com.trungtamtienganh.dto.NameSlugDTO;
import com.trungtamtienganh.service.ExamService;

@RestController
@RequestMapping(value = "/exams")
@CrossOrigin
public class ExamController {

	@Autowired
	private ExamService examService;

	@GetMapping("/{slug}")
	public ExamQuestionDTO getExamQuestionBySlug(@PathVariable("slug") String examSlug) {

		return examService.getExamQuestion(examSlug);
	}

	@PostMapping("/{slug}/result")
	public ExamResultDTO checkResult(@PathVariable("slug") String slug, @RequestBody Map<Integer, String> answers) {

		return examService.getExamResult(slug, answers);
	}

	@GetMapping("")
	public List<NameSlugDTO> getNameSlugOfExams() {

		return examService.getListNameSlugs();
	}

	@GetMapping("/{slug}/parts")
	public List<Object> getQuestionsOfPart(@PathVariable("slug") String examSlug, @RequestParam("type") int type) {

		return examService.getQuestionsOfPart(examSlug, type);
	}

}