package com.trungtamtienganh.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.dto.ParagraphDTO;
import com.trungtamtienganh.entity.Paragraph;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.ParagraphRepository;
import com.trungtamtienganh.service.AwsS3Service;
import com.trungtamtienganh.service.ParagraphService;
import com.trungtamtienganh.utils.MyConstant;

@Service
@Transactional
public class ParagraphServiceImpl implements ParagraphService {

	@Autowired
	private ParagraphRepository paragraphRepository;
	@Autowired
	private AwsS3Service awsS3Service;

	@Override
	public ParagraphDTO save(ParagraphDTO paragraphDTO) {

		if (paragraphDTO == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		int id = paragraphDTO.getId();
		Paragraph paragraph = paragraphRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.PARAGRAPH));

		int type = paragraph.getNumberPart();

		if (type == 6 || type == 7)
			paragraph.setContent(paragraphDTO.getContent());

		paragraph.setTranscript(paragraphDTO.getTranscript());

		paragraphRepository.save(paragraph);

		return toParagraphDTO(paragraphRepository.save(paragraph));
	}

	public ParagraphDTO toParagraphDTO(Paragraph paragraph) {

		ParagraphDTO paragraphDTO = new ParagraphDTO();
		paragraphDTO.setId(paragraph.getId());
		paragraphDTO.setContent(paragraph.getContent());
		paragraphDTO.setImage(paragraph.getImage());
		paragraphDTO.setTranscript(paragraph.getTranscript());

		return paragraphDTO;
	}

	@Override
	public String uploadAudio(int id, MultipartFile audioFile) {

		if (id <= 0 || audioFile == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Paragraph paragraph = paragraphRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.PARAGRAPH));

		int numberPart = paragraph.getNumberPart();

		if (numberPart != 3 && numberPart != 4)
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.PARAGRAPH);

		String audio = paragraph.getContent();

		if (audio != null) {
			awsS3Service.deleteObjectFromUrl(audio);
		}

		String audioUpload = awsS3Service.uploadObject(audioFile);

		paragraph.setContent(audioUpload);
		paragraphRepository.save(paragraph);

		return audioUpload;
	}

	@Override
	public String uploadImage(int id, MultipartFile imageFile) {

		if (id <= 0 || imageFile == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Paragraph paragraph = paragraphRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.PARAGRAPH));

		String image = paragraph.getImage();
		if (image != null) {
			awsS3Service.deleteObjectFromUrl(image);
		}

		String imageUpload = awsS3Service.uploadObject(imageFile);

		paragraph.setImage(imageUpload);
		paragraphRepository.save(paragraph);

		return imageUpload;
	}
}