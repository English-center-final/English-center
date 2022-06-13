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

import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.WordDTO;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.service.WordService;
import com.trungtamtienganh.utils.MyConstant;
import com.trungtamtienganh.utils.RestConstant;

@RestController
@RequestMapping("/admin/courses/words")
@CrossOrigin
public class WordAdminController {

	@Autowired
	private WordService wordService;

	@GetMapping("")
	public PaginationWrapper<List<WordDTO>> getWords(
			@RequestParam(name = "name", required = false, defaultValue = "") String name,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {

		return wordService.getList(name, page, size);
	}

	@PostMapping(value = "", consumes = RestConstant.CONSUMES_JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public WordDTO addWord(@Valid @RequestBody WordDTO wordDTO) {

		wordDTO.setId(0);
		return wordService.save(wordDTO);
	}

	@PutMapping(value = "/{id}", consumes = RestConstant.CONSUMES_JSON)
	public WordDTO updateWord(@PathVariable("id") Integer id, @Valid @RequestBody WordDTO wordDTO) {

		if (id <= 0)
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.WORD);

		wordDTO.setId(id);
		return wordService.save(wordDTO);
	}

	@PutMapping(value = "/{id}/image")
	public String updateImage(@PathVariable("id") Integer id, @RequestParam("image") MultipartFile image) {

		String fileName = wordService.uploadImage(id, image);
		return fileName;
	}

	@PutMapping(value = "/{id}/sound")
	public String updateSound(@PathVariable("id") Integer id, @RequestParam("sound") MultipartFile sound) {

		String fileName = wordService.uploadSound(id, sound);
		return fileName;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteWord(@PathVariable("id") Integer id) {

		wordService.delete(id);
	}
}
