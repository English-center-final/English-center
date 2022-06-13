package com.trungtamtienganh.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.WordDTO;

public interface WordService {

	PaginationWrapper<List<WordDTO>> getList(String name, int page, int size);

	WordDTO save(WordDTO wordDTO);

	String uploadImage(Integer id, MultipartFile image);

	String uploadSound(Integer id, MultipartFile sound);

	void delete(Integer id);
}
