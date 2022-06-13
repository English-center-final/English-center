package com.trungtamtienganh.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trungtamtienganh.dto.QuestionDTO;
import com.trungtamtienganh.dto.QuestionResultDTO;
import com.trungtamtienganh.dto.QuestionSummaryDTO;
import com.trungtamtienganh.entity.Audio;
import com.trungtamtienganh.entity.Question;
import com.trungtamtienganh.repository.QuestionRepository;

@Component
public class QuestionConverter {

	@Autowired
	private QuestionRepository questionRepository;

	public QuestionSummaryDTO toQuestionSummaryDTO(Question question) {

		var result = new QuestionSummaryDTO();
		result.setId(question.getId());
		result.setStt(question.getStt());
		result.setContent(question.getContent());
		result.setA(question.getA());
		result.setB(question.getB());
		result.setC(question.getC());
		result.setD(question.getD());

		return result;
	}

	public QuestionDTO toQuestionDTO(Question question) {

		var result = new QuestionDTO();
		result.setId(question.getId());
		result.setStt(question.getStt());
		result.setContent(question.getContent());
		result.setA(question.getA());
		result.setB(question.getB());
		result.setC(question.getC());
		result.setD(question.getD());

		Audio audio = question.getAudio();
		String audioName = audio != null ? audio.getName() : null;
		result.setAudio(audioName);

		result.setType(question.getType());
		result.setResult(question.getResult());
		result.setExtra(question.getExtra());

		return result;
	}

	public QuestionResultDTO toQuestionResultDTO(Question question, String choice) {

		QuestionResultDTO result = new QuestionResultDTO();

		result.setChoice(choice);
		result.setQuestion(toQuestionDTO(question));

		return result;
	}

	public Question toQuestion(QuestionDTO questionDTO) {

		Question question = questionRepository.findById(questionDTO.getId()).get();

		int type = question.getType();

		if (type != 1)
			question.setContent(questionDTO.getContent());

		question.setA(questionDTO.getA());
		question.setB(questionDTO.getB());
		question.setC(questionDTO.getC());
		question.setD(type == 2 ? null : questionDTO.getD());
		question.setResult(questionDTO.getResult());
		question.setExtra(questionDTO.getExtra());

		return question;
	}

}
