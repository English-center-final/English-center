package com.trungtamtienganh.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.trungtamtienganh.dto.UserExamDTO;
import com.trungtamtienganh.entity.UserExam;

@Component
public class UserExamConverter {

	public UserExamDTO toUserExamDTO(UserExam userExam) {
		UserExamDTO userExamDTO = new UserExamDTO();
		
		userExamDTO.setId(userExam.getId());
		userExamDTO.setUserId(userExam.getUser().getId());
		userExamDTO.setExamId(userExam.getExam().getId());
		userExamDTO.setExamName(userExam.getExam().getName());
		userExamDTO.setBookName(userExam.getExam().getBook().getName());
		userExamDTO.setSlug(userExam.getExam().getSlug());
		
		userExamDTO.setPartResult(toPartResult(userExam.getPartResult()));
		
		userExamDTO.setListenPoint(userExam.getListenPoint());
		userExamDTO.setReadPoint(userExam.getReadPoint());
		userExamDTO.setTestDate(userExam.getTestDate());

		return userExamDTO;
	}
	
	
	public List<Integer> toPartResult(String partResultStr) {
		
		String[] splitArray = partResultStr.split(", ");
		
		List<Integer> partResult = new ArrayList<Integer>();
		
		for (int i = 0; i < splitArray.length; i++) {
			partResult.add(Integer.parseInt(splitArray[i]));
        }

		return partResult;
	}
}
