package com.trungtamtienganh.utils;

import javax.persistence.PostRemove;

import org.springframework.beans.factory.annotation.Autowired;

import com.trungtamtienganh.entity.Audio;
import com.trungtamtienganh.service.AwsS3Service;


public class AudioAuditListener {

	@Autowired
	private AwsS3Service awsS3Service;

	@PostRemove
	private void removeAudio(Audio audio) {

		String audioUrl = audio.getName();
		if (audioUrl != null && audioUrl.length() > 0) {
			awsS3Service.deleteObjectFromUrl(audioUrl);
		}
	}

}
