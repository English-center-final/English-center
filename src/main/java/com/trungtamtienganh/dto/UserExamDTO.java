package com.trungtamtienganh.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserExamDTO {
	
	private Integer id;
	
	private Integer userId;
	
	private Integer examId;
	private String examName;
	private String bookName;
	private String slug;
	
	private List<Integer> partResult;
	
	private int listenPoint;
	private int readPoint;
	private LocalDateTime testDate;
}
