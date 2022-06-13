package com.trungtamtienganh.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.converter.BookConverter;
import com.trungtamtienganh.dto.BookDTO;
import com.trungtamtienganh.entity.Book;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.BookRepository;
import com.trungtamtienganh.service.AwsS3Service;
import com.trungtamtienganh.service.BookService;
import com.trungtamtienganh.utils.MyConstant;


@Service
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookConverter bookConverter;

	@Autowired
	private AwsS3Service awsS3Service;

	@Override
	public List<BookDTO> getAll() {

		return bookRepository.findAll().stream().map(book -> bookConverter.toBookDTO(book))
				.collect(Collectors.toList());
	}

	@Override
	public BookDTO save(BookDTO bookDTO) {
		validate(bookDTO);

		Book bookWasSave = bookRepository.save(bookConverter.toBook(bookDTO));

		return bookConverter.toBookDTO(bookWasSave);
	}

	private void validate(BookDTO bookDTO) {
		if (bookDTO == null || bookDTO.getId() < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Integer id = bookDTO.getId();

		if (id != 0 && !bookRepository.existsById(id))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.BOOK);

		if (bookRepository.existsByIdNotAndName(id, bookDTO.getName())) {
			Map<String, String> error = new HashMap<>();
			error.put("name", "Tên sách bị trùng");
			throw MyExceptionHelper.throwRuntimeCustomException(error);
		}

	}

	@Override
	public void delete(Integer id) {

		if (id == null || id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.BOOK));

		String image = book.getImage();
		bookRepository.deleteById(id);

		if (image != null) {

			awsS3Service.deleteObjectFromUrl(image);
		}

	}

	@Override
	public String uploadImage(Integer id, MultipartFile image) {
		if (id == null || image == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.BOOK));

		String imageString = book.getImage();
		if (imageString != null) {

			awsS3Service.deleteObjectFromUrl(imageString);
		}

		String blogImage = awsS3Service.uploadObject(image);
		book.setImage(blogImage);

		bookRepository.save(book);

		return blogImage;
	}

}