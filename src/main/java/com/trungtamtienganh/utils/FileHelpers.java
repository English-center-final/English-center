package com.trungtamtienganh.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileHelpers {

	public static File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {

		File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
		multipartFile.transferTo(convFile);

		return convFile;
	}

	public static String generateFileName(String fileName) {

		long currentTimeMillis = System.currentTimeMillis();
		String stringRandom = RandomStringUtils.randomAlphanumeric(10);

		String extension = FilenameUtils.getExtension(fileName);
		String fileNameTempt = CommonFuc.toSlug(fileName) + "." + extension;

		return currentTimeMillis + "-" + stringRandom + "-" + fileNameTempt;
	}

	public static boolean checkImageExtension(MultipartFile file) {

		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png"))
			return false;
		return true;
	}

	public static String getKeyS3FromUrl(String url) {

		String[] urlSplit = url.split("/");
		String key = urlSplit[urlSplit.length - 1];
		return key;
	}
}