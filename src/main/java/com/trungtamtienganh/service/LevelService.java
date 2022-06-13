package com.trungtamtienganh.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.dto.LevelDTO;

public interface LevelService {
	List<LevelDTO> getList(String name);

	LevelDTO save(LevelDTO routeDTO);

	LevelDTO getBySlug(String slug);

	String uploadImage(Integer id, MultipartFile image);

	void delete(Integer id);

}
