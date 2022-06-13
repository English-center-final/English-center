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

import com.trungtamtienganh.dto.BookDTO;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.service.BookService;
import com.trungtamtienganh.utils.MyConstant;
import com.trungtamtienganh.utils.RestConstant;

@RestController
@RequestMapping("/admin/exams/books")
@CrossOrigin
public class BookAdminController {

	@Autowired
	private BookService bookService;

	@PostMapping(value = "", consumes = RestConstant.CONSUMES_JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public BookDTO addBook(@Valid @RequestBody BookDTO bookDTO) {

		bookDTO.setId(0);
		return bookService.save(bookDTO);
	}

	@PutMapping(value = "/{id}", consumes = RestConstant.CONSUMES_JSON)
	public BookDTO updateBook(@PathVariable("id") Integer id, @Valid @RequestBody BookDTO bookDTO) {

		if (id <= 0)
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.BOOK);

		bookDTO.setId(id);
		return bookService.save(bookDTO);
	}

	@PutMapping(value = "/{id}/image")
	public String updateImage(@PathVariable("id") Integer id, @RequestParam("image") MultipartFile image) {

		String fileName = bookService.uploadImage(id, image);
		return fileName;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteBook(@PathVariable("id") Integer id) {

		bookService.delete(id);
	}
}