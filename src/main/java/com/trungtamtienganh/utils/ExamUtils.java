package com.trungtamtienganh.utils;

import com.trungtamtienganh.entity.Exam;
import com.trungtamtienganh.entity.Paragraph;
import com.trungtamtienganh.entity.Question;

public class ExamUtils {

	public static boolean checkStt(int type, int stt) {

		switch (type) {
		case 1:

			if (stt >= MyConstant.PART1_START_STT && stt <= MyConstant.PART1_END_STT)
				return true;

			break;

		case 2:
			if (stt >= MyConstant.PART2_START_STT && stt <= MyConstant.PART2_END_STT)
				return true;

			break;

		case 3:
			if (stt >= MyConstant.PART3_START_STT && stt <= MyConstant.PART3_END_STT)
				return true;
			break;

		case 4:
			if (stt >= MyConstant.PART4_START_STT && stt <= MyConstant.PART4_END_STT)
				return true;
			break;

		case 5:
			if (stt >= MyConstant.PART5_START_STT && stt <= MyConstant.PART5_END_STT)
				return true;
			break;

		case 6:
			if (stt >= MyConstant.PART6_START_STT && stt <= MyConstant.PART6_END_STT)
				return true;
			break;

		case 7:
			if (stt >= MyConstant.PART7_START_STT && stt <= MyConstant.PART7_END_STT)
				return true;
			break;

		default:

			return false;
		}

		return false;
	}

	public static Question createQuestionEmpty(int stt, int type, int examId) {
		Question question = new Question();

		question.setStt(stt);
		question.setType(type);
		question.setContent("");
		question.setA("");
		question.setB("");
		question.setC("");
		question.setD(type == 2 ? null : "");
		question.setResult("");
		question.setExtra("");
		question.setExam(new Exam(examId));

		return question;
	}

	public static Paragraph createEmptyParagrap() {

		return null;
	}
}