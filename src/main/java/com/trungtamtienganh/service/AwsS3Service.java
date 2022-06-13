package com.trungtamtienganh.service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {
	String uploadObject(MultipartFile multipartFile);

	void deleteObjectFromUrl(String url);
}
