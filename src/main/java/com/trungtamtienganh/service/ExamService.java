package com.trungtamtienganh.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.dto.ExamDTO;
import com.trungtamtienganh.dto.ExamQuestionDTO;
import com.trungtamtienganh.dto.ExamResultDTO;
import com.trungtamtienganh.dto.NameSlugDTO;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.UserExamDTO;

public interface ExamService {

	ExamQuestionDTO getExamQuestion(String slug);

	ExamResultDTO getExamResult(String slug, Map<Integer, String> answers);

	List<NameSlugDTO> getListNameSlugs();

	List<Object> getQuestionsOfPart(String slug, int type);

	PaginationWrapper<List<ExamDTO>> getList(String name, String bookName, int page, int size);

	ExamDTO add(ExamDTO examDTO, int[] stts);

	ExamDTO save(ExamDTO examDTO);

	void delete(Integer id);

	void uploadAudio(int id, MultipartFile part1Audio, MultipartFile part2Audio, MultipartFile part3Audio,
			MultipartFile part4Audio);
	
	PaginationWrapper<List<UserExamDTO>> getListUserExam(int page, int size);

}