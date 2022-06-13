package com.trungtamtienganh.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.dto.ExamDTO;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.service.ExamService;
import com.trungtamtienganh.utils.MyConstant;
import com.trungtamtienganh.utils.RestConstant;


@RestController
@RequestMapping("/admin/exams")
@CrossOrigin
public class ExamAdminController {

	@Autowired
	private ExamService examService;

	@GetMapping("")
	public PaginationWrapper<List<ExamDTO>> getWords(
			@RequestParam(name = "name", required = false, defaultValue = "") String name,
			@RequestParam(name = "bookName", required = false, defaultValue = "") String bookSlug,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {

		return examService.getList(name, bookSlug, page, size);
	}

	@PostMapping(value = "", consumes = RestConstant.CONSUMES_JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public ExamDTO add(@Valid @RequestBody ExamDTO examDTO, int[] stts) {

		examDTO.setId(0);
		return examService.add(examDTO, stts);
	}

	@PutMapping(value = "/{id}", consumes = RestConstant.CONSUMES_JSON)
	public ExamDTO update(@PathVariable("id") Integer id, @Valid @RequestBody ExamDTO examDTO) {

		if (id <= 0)
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.EXAM);

		examDTO.setId(id);
		return examService.save(examDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Integer id) {

		examService.delete(id);
	}

	@PutMapping(value = "/{id}/audio")
	public void updateAudio(@PathVariable("id") int id,
			@RequestParam(name = "part1Audio", required = false) MultipartFile part1Audio,
			@RequestParam(name = "part2Audio", required = false) MultipartFile part2Audio,
			@RequestParam(name = "part3Audio", required = false) MultipartFile part3Audio,
			@RequestParam(name = "part4Audio", required = false) MultipartFile part4Audio

	) {

		examService.uploadAudio(id, part1Audio, part2Audio, part3Audio, part4Audio);
	}

}