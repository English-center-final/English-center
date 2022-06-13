package com.trungtamtienganh.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.dto.ParagraphDTO;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.service.ParagraphService;
import com.trungtamtienganh.utils.RestConstant;

@RestController
@RequestMapping("/admin/exams/paragraphs")
@CrossOrigin
public class ParagraphAdminController {

	@Autowired
	private ParagraphService paragraphService;

	@PutMapping(value = "/{id}", consumes = RestConstant.CONSUMES_JSON)
	public ParagraphDTO update(@PathVariable("id") int id, @Valid @RequestBody ParagraphDTO paragraphDTO) {

		if (id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		paragraphDTO.setId(id);
		return paragraphService.save(paragraphDTO);
	}

	@PutMapping(value = "/{id}/audio")
	public String uploadAudio(@PathVariable("id") int id, @RequestParam("audio") MultipartFile audioFile) {

		return paragraphService.uploadAudio(id, audioFile);
	}

	@PutMapping(value = "/{id}/image")
	public String uploadImage(@PathVariable("id") int id, @RequestParam("image") MultipartFile imageFile) {

		return paragraphService.uploadImage(id, imageFile);
	}

}