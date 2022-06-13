package com.trungtamtienganh.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.trungtamtienganh.service.AwsS3Service;
import com.trungtamtienganh.utils.FileHelpers;

@Service
@Transactional
public class AwsS3ServiceImpl implements AwsS3Service {

	@Value("${AWS_BUCKET_NAME}")
	private String BUCKET_NAME;
	@Value("${AWS_ENDPOINT}")
	private String ENDPOINT_URL;
	@Value("${AWS_ACCESS_KEY}")
	private String ACCESS_KEY;
	@Value("${AWS_SECRET_KEY}")
	private String SECRET_KEY;
	
	private AmazonS3 s3Client;

	@PostConstruct
	private void initAwsS3() {
		AWSCredentials credentials = new BasicAWSCredentials(this.ACCESS_KEY, this.SECRET_KEY);
		this.s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.AP_SOUTHEAST_1).build();
	}

	@Override
	public String uploadObject(MultipartFile multipartFile) {

		try {

			File file = FileHelpers.convertMultiPartToFile(multipartFile);
			String fileName = FileHelpers.generateFileName(multipartFile.getOriginalFilename());
			this.s3Client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, file)
					.withCannedAcl(CannedAccessControlList.PublicRead));

			String fileUrl = ENDPOINT_URL + "/" + BUCKET_NAME + "/" + fileName;
			return fileUrl;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteObjectFromUrl(String url) {

		String key = FileHelpers.getKeyS3FromUrl(url);

		try {

			s3Client.deleteObject(BUCKET_NAME, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
