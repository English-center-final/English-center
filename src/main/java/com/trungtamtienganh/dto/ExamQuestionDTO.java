package com.trungtamtienganh.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamQuestionDTO extends ExamCommonInfoDTO {

	private List<Part1_2QuestionDTO> part1 = new ArrayList<>();
	private List<Part1_2QuestionDTO> part2 = new ArrayList<>();
	private List<Part3_4_6_7QuestionGroupDTO> part3 = new ArrayList<>();
	private List<Part3_4_6_7QuestionGroupDTO> part4 = new ArrayList<>();
	private List<QuestionSummaryDTO> part5 = new ArrayList<>();
	private List<Part3_4_6_7QuestionGroupDTO> part6 = new ArrayList<>();
	private List<Part3_4_6_7QuestionGroupDTO> part7 = new ArrayList<>();

}
