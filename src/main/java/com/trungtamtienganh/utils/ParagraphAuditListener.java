package com.trungtamtienganh.utils;

import javax.persistence.PostRemove;

import org.springframework.beans.factory.annotation.Autowired;

import com.trungtamtienganh.entity.Paragraph;
import com.trungtamtienganh.service.AwsS3Service;

public class ParagraphAuditListener {

	@Autowired
	private AwsS3Service awsS3Service;

	@PostRemove
	private void removeParagraph(Paragraph paragraph) {

		String imageUrl = paragraph.getImage();
		
		if (imageUrl != null && imageUrl.length() > 0) {
			awsS3Service.deleteObjectFromUrl(imageUrl);
		}

		int numberPart = paragraph.getNumberPart();
		if (numberPart != 3 && numberPart != 4)
			return;

		String audioUrl = paragraph.getContent();

		if (audioUrl != null && audioUrl.length() > 0) {
			awsS3Service.deleteObjectFromUrl(audioUrl);
		}

	}
}