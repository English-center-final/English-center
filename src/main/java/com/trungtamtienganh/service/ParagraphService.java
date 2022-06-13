package com.trungtamtienganh.service;

import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.dto.ParagraphDTO;

public interface ParagraphService {

	ParagraphDTO save(ParagraphDTO paragraphDTO);

	String uploadAudio(int id, MultipartFile audioFile);

	String uploadImage(int id, MultipartFile imageFile);
}
