package com.trungtamtienganh.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.dto.QuestionDTO;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.service.QuestionService;
import com.trungtamtienganh.utils.RestConstant;


@RestController
@RequestMapping("/admin/exams/questions")
@CrossOrigin
public class QuestionAdminController {

	@Autowired
	private QuestionService questionService;

	@GetMapping("")
	public List<?> getPart1_2_5(@RequestParam("examId") int examId, @RequestParam("type") int type) {

		if (type < 1 || type > 7)
			throw MyExceptionHelper.throwIllegalArgumentException();

		if (type == 1 || type == 2 || type == 5)
			return questionService.getList(type, examId);

		return questionService.getListGroup(type, examId);
	}

	@PutMapping(value = "/{id}", consumes = RestConstant.CONSUMES_JSON)
	public QuestionDTO update(@PathVariable("id") int id, @Valid @RequestBody QuestionDTO questionDTO) {

		if (id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		questionDTO.setId(id);
		return questionService.save(questionDTO);
	}

	@PutMapping(value = "/{id}/audio")
	public String uploadAudio(@PathVariable("id") int id, @RequestParam("audio") MultipartFile audioFile) {

		return questionService.uploadAudio(id, audioFile);
	}

	@PutMapping(value = "/{id}/image")
	public String uploadImageForPart1(@PathVariable("id") int id, @RequestParam("image") MultipartFile imageFile) {

		return questionService.uploadImageForPart1(id, imageFile);
	}

}