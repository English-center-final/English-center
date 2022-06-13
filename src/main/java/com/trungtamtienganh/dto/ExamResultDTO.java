package com.trungtamtienganh.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamResultDTO extends ExamCommonInfoDTO {

	private int part1Number;
	private int part2Number;
	private int part3Number;
	private int part4Number;
	private int listenNumber;
	private int listenPoint;

	private int part5Number;
	private int part6Number;
	private int part7Number;
	private int readNumber;
	private int readPoint;

	private List<QuestionResultDTO> part1;
	private List<QuestionResultDTO> part2;
	private List<Part3_4_6_7ResultGroupDTO> part3;
	private List<Part3_4_6_7ResultGroupDTO> part4;
	private List<QuestionResultDTO> part5;
	private List<Part3_4_6_7ResultGroupDTO> part6;
	private List<Part3_4_6_7ResultGroupDTO> part7;

}