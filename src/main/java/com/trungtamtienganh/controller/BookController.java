package com.trungtamtienganh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamtienganh.dto.BookDTO;
import com.trungtamtienganh.service.BookService;

@RestController
@RequestMapping(value = "/books")
@CrossOrigin
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping("")
	public List<BookDTO> getBookInfos() {

		return bookService.getAll();
	}

}
