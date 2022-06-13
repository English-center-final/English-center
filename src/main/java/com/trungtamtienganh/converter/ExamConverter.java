package com.trungtamtienganh.converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trungtamtienganh.dto.ExamDTO;
import com.trungtamtienganh.dto.ExamQuestionDTO;
import com.trungtamtienganh.dto.ExamResultDTO;
import com.trungtamtienganh.dto.Part1_2QuestionDTO;
import com.trungtamtienganh.dto.Part3_4_6_7GroupDTO;
import com.trungtamtienganh.dto.Part3_4_6_7QuestionGroupDTO;
import com.trungtamtienganh.dto.Part3_4_6_7ResultGroupDTO;
import com.trungtamtienganh.dto.QuestionResultDTO;
import com.trungtamtienganh.dto.QuestionSummaryDTO;
import com.trungtamtienganh.entity.Audio;
import com.trungtamtienganh.entity.Book;
import com.trungtamtienganh.entity.Exam;
import com.trungtamtienganh.entity.Paragraph;
import com.trungtamtienganh.entity.Question;
import com.trungtamtienganh.entity.QuestionParagraph;
import com.trungtamtienganh.repository.ExamRepository;
import com.trungtamtienganh.repository.ParagraphRepository;
import com.trungtamtienganh.repository.QuestionRepository;
import com.trungtamtienganh.utils.CommonFuc;
import com.trungtamtienganh.utils.ToeicPoint;

@Component
public class ExamConverter {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private ParagraphRepository paragraphRepository;
	@Autowired
	private QuestionConverter questionConverter;
	@Autowired
	private ExamRepository examRepository;

	public ExamQuestionDTO toExamQuestionDTO(Exam exam) {

		ExamQuestionDTO result = new ExamQuestionDTO();

		result.setName(exam.getName());
		result.setSlug(exam.getSlug());
//		result.setPart1Audio(exam.getPart1Audio());
//		result.setPart2Audio(exam.getPart2Audio());
//		result.setPart3Audio(exam.getPart3Audio());
//		result.setPart4Audio(exam.getPart4Audio());

		Integer examId = exam.getId();
		List<Question> part1Questions = questionRepository.findAllByTypeAndExamIdOrderByStt(1, examId);
		List<Question> part2Questions = questionRepository.findAllByTypeAndExamIdOrderByStt(2, examId);
		List<Question> part5Questions = questionRepository.findAllByTypeAndExamIdOrderByStt(5, examId);

		List<Paragraph> paragraphsOfPart3 = paragraphRepository.findAllByExamIdAndQuestionType(examId, 3);
		List<Paragraph> paragraphsOfPart4 = paragraphRepository.findAllByExamIdAndQuestionType(examId, 4);
		List<Paragraph> paragraphsOfPart6 = paragraphRepository.findAllByExamIdAndQuestionType(examId, 6);
		List<Paragraph> paragraphsOfPart7 = paragraphRepository.findAllByExamIdAndQuestionType(examId, 7);

		// part1
		List<Part1_2QuestionDTO> part1QuestionsOfResult = part1Questions.stream()
				.map(question -> toPart1_2QuestionResponse(question)).collect(Collectors.toList());
		result.setPart1(part1QuestionsOfResult);

		// part2
		List<Part1_2QuestionDTO> part2QuestionsOfResult = part2Questions.stream()
				.map(question -> toPart1_2QuestionResponse(question)).collect(Collectors.toList());
		result.setPart2(part2QuestionsOfResult);

		// part3
		List<Part3_4_6_7QuestionGroupDTO> part3GroupsOfResult = paragraphsOfPart3.stream()
				.map(paragraph -> toPart3_4_6_7QuestionGroupDTO(paragraph)).collect(Collectors.toList());
		result.setPart3(part3GroupsOfResult);

		// part4
		List<Part3_4_6_7QuestionGroupDTO> part4GroupsOfResult = paragraphsOfPart4.stream()
				.map(paragraph -> toPart3_4_6_7QuestionGroupDTO(paragraph)).collect(Collectors.toList());
		result.setPart4(part4GroupsOfResult);

		// part5
		List<QuestionSummaryDTO> part5QuestionsOfResult = part5Questions.stream()
				.map(question -> questionConverter.toQuestionSummaryDTO(question)).collect(Collectors.toList());
		result.setPart5(part5QuestionsOfResult);

		// part 6
		List<Part3_4_6_7QuestionGroupDTO> part6GroupsOfResult = paragraphsOfPart6.stream()
				.map(paragraph -> toPart3_4_6_7QuestionGroupDTO(paragraph)).collect(Collectors.toList());
		result.setPart6(part6GroupsOfResult);

		// part 7
		List<Part3_4_6_7QuestionGroupDTO> part7GroupsOfResult = paragraphsOfPart7.stream()
				.map(paragraph -> toPart3_4_6_7QuestionGroupDTO(paragraph)).collect(Collectors.toList());
		result.setPart7(part7GroupsOfResult);

		return result;

	}

	public ExamResultDTO toExamResultDTO(Exam exam, Map<Integer, String> answers) {

		ExamResultDTO result = new ExamResultDTO();
		result.setName(exam.getName());
		result.setSlug(exam.getSlug());
//		result.setPart1Audio(exam.getPart1Audio());
//		result.setPart2Audio(exam.getPart2Audio());
//		result.setPart3Audio(exam.getPart3Audio());
//		result.setPart4Audio(exam.getPart4Audio());

		Integer examId = exam.getId();
		List<Question> part1Questions = questionRepository.findAllByTypeAndExamIdOrderByStt(1, examId);
		List<Question> part2Questions = questionRepository.findAllByTypeAndExamIdOrderByStt(2, examId);
		List<Question> part5Questions = questionRepository.findAllByTypeAndExamIdOrderByStt(5, examId);

		List<Paragraph> paragraphsOfPart3 = paragraphRepository.findAllByExamIdAndQuestionType(examId, 3);
		List<Paragraph> paragraphsOfPart4 = paragraphRepository.findAllByExamIdAndQuestionType(examId, 4);
		List<Paragraph> paragraphsOfPart6 = paragraphRepository.findAllByExamIdAndQuestionType(examId, 6);
		List<Paragraph> paragraphsOfPart7 = paragraphRepository.findAllByExamIdAndQuestionType(examId, 7);

		// part1
		List<QuestionResultDTO> part1QuestionsOfResult = part1Questions.stream().map(question -> {

			String choice = answers.get(question.getStt());

			return questionConverter.toQuestionResultDTO(question, choice);

		}).collect(Collectors.toList());
		result.setPart1(part1QuestionsOfResult);

		// part2
		List<QuestionResultDTO> part2QuestionsOfResult = part2Questions.stream().map(question -> {

			String choice = answers.get(question.getStt());

			return questionConverter.toQuestionResultDTO(question, choice);

		}).collect(Collectors.toList());
		result.setPart2(part2QuestionsOfResult);

		// part3
		List<Part3_4_6_7ResultGroupDTO> part3GroupsOfResult = paragraphsOfPart3.stream()
				.map(paragraph -> toPart3_4_6_7ResultGroupDTO(paragraph, answers)).collect(Collectors.toList());
		result.setPart3(part3GroupsOfResult);

		// part4
		List<Part3_4_6_7ResultGroupDTO> part4GroupsOfResult = paragraphsOfPart4.stream()
				.map(paragraph -> toPart3_4_6_7ResultGroupDTO(paragraph, answers)).collect(Collectors.toList());
		result.setPart4(part4GroupsOfResult);

		// part5
		List<QuestionResultDTO> part5QuestionsOfResult = part5Questions.stream().map(question -> {

			String choice = answers.get(question.getStt());

			return questionConverter.toQuestionResultDTO(question, choice);

		}).collect(Collectors.toList());
		result.setPart5(part5QuestionsOfResult);

		// part 6
		List<Part3_4_6_7ResultGroupDTO> part6GroupsOfResult = paragraphsOfPart6.stream()
				.map(paragraph -> toPart3_4_6_7ResultGroupDTO(paragraph, answers)).collect(Collectors.toList());
		result.setPart6(part6GroupsOfResult);

		// part 7
		List<Part3_4_6_7ResultGroupDTO> part7GroupsOfResult = paragraphsOfPart7.stream()
				.map(paragraph -> toPart3_4_6_7ResultGroupDTO(paragraph, answers)).collect(Collectors.toList());
		result.setPart7(part7GroupsOfResult);

		// get so cau dung
		int part1Number = countCorrectNumber(part1QuestionsOfResult);
		int part2Number = countCorrectNumber(part2QuestionsOfResult);
		int part3Number = countCorrectNumberOfPart3_4_6_7(part3GroupsOfResult);
		int part4Number = countCorrectNumberOfPart3_4_6_7(part4GroupsOfResult);
		int listenNumber = part1Number + part2Number + part3Number + part4Number;
		int listenPoint = ToeicPoint.getPoint(listenNumber, 0);

		int part5Number = countCorrectNumber(part5QuestionsOfResult);
		int part6Number = countCorrectNumberOfPart3_4_6_7(part6GroupsOfResult);
		int part7Number = countCorrectNumberOfPart3_4_6_7(part7GroupsOfResult);
		int readNumber = part5Number + part6Number + part7Number;
		int readPoint = ToeicPoint.getPoint(readNumber, 1);

		result.setPart1Number(part1Number);
		result.setPart2Number(part2Number);
		result.setPart3Number(part3Number);
		result.setPart4Number(part4Number);
		result.setListenNumber(listenNumber);
		result.setListenPoint(listenPoint);

		result.setPart5Number(part5Number);
		result.setPart6Number(part6Number);
		result.setPart7Number(part7Number);
		result.setReadNumber(readNumber);
		result.setReadPoint(readPoint);

		return result;
	}

	private int countCorrectNumberOfPart3_4_6_7(List<Part3_4_6_7ResultGroupDTO> part3_4_6_7ResultGroupDTOs) {

		int count = 0;

		for (Part3_4_6_7ResultGroupDTO Part3_4_6_7ResultGroupDTOTempt : part3_4_6_7ResultGroupDTOs) {

			for (QuestionResultDTO tempt : Part3_4_6_7ResultGroupDTOTempt.getResults())
				if (tempt.isCorrect())
					count++;

		}

		return count;
	}

	private int countCorrectNumber(List<QuestionResultDTO> questionResultDTOs) {

		int count = 0;

		for (QuestionResultDTO tempt : questionResultDTOs)
			if (tempt.isCorrect())
				count++;

		return count;
	}

	public Part1_2QuestionDTO toPart1_2QuestionResponse(Question question) {

		Part1_2QuestionDTO result = new Part1_2QuestionDTO();

		result.setStt(question.getStt());

		if (question.getType() == 1) {
			result.setContent(question.getContent());
		}

		Audio audio = question.getAudio();
		String audioName = audio != null ? audio.getName() : "";
		result.setAudio(audioName);

		return result;
	}

	public Part3_4_6_7QuestionGroupDTO toPart3_4_6_7QuestionGroupDTO(Paragraph paragraph) {

		Part3_4_6_7QuestionGroupDTO result = new Part3_4_6_7QuestionGroupDTO();

		result.setImage(paragraph.getImage());
		result.setParagraph(paragraph.getContent());

		for (QuestionParagraph questionParagraph : paragraph.getQuestionParagraphs()) {

			Question question = questionParagraph.getQuestion();
			result.getQuestions().add(questionConverter.toQuestionSummaryDTO(question));
		}

		return result;
	}

	public Part3_4_6_7GroupDTO toPart3_4_6_7GroupDTO(Paragraph paragraph) {

		Part3_4_6_7GroupDTO result = new Part3_4_6_7GroupDTO();

		result.setId(paragraph.getId());
		result.setImage(paragraph.getImage());
		result.setParagraph(paragraph.getContent());
		result.setTranscript(paragraph.getTranscript());

		for (QuestionParagraph questionParagraph : paragraph.getQuestionParagraphs()) {

			Question question = questionParagraph.getQuestion();
			result.getQuestions().add(questionConverter.toQuestionDTO(question));
		}

		return result;
	}

	public Part3_4_6_7ResultGroupDTO toPart3_4_6_7ResultGroupDTO(Paragraph paragraph, Map<Integer, String> answers) {

		Part3_4_6_7ResultGroupDTO result = new Part3_4_6_7ResultGroupDTO();
		result.setImage(paragraph.getImage());
		result.setParagraph(paragraph.getContent());
		result.setTranscript(paragraph.getTranscript());

		for (QuestionParagraph questionParagraph : paragraph.getQuestionParagraphs()) {

			Question question = questionParagraph.getQuestion();

			String choice = answers.get(question.getStt());
			QuestionResultDTO questionResultDTOTempt = questionConverter.toQuestionResultDTO(question, choice);

			result.getResults().add(questionResultDTOTempt);
		}

		return result;
	}

	public ExamDTO toExamDTO(Exam exam) {

		var examDTO = new ExamDTO();

		examDTO.setId(exam.getId());
		examDTO.setName(exam.getName());
		examDTO.setSlug(exam.getSlug());
//		examDTO.setPart1Audio(exam.getPart1Audio());
//		examDTO.setPart2Audio(exam.getPart2Audio());
//		examDTO.setPart3Audio(exam.getPart3Audio());
//		examDTO.setPart4Audio(exam.getPart4Audio());

		Book book = exam.getBook();
		examDTO.setBookId(book.getId());
		examDTO.setBookName(book.getName());

		return examDTO;
	}

	public Exam toExam(ExamDTO examDTO) {

		var exam = examRepository.findById(examDTO.getId()).orElse(new Exam(0));

		String name = examDTO.getName();
		exam.setName(name);
		exam.setSlug(CommonFuc.toSlug(name));

		exam.setBook(new Book(examDTO.getBookId(), examDTO.getBookName()));

		return exam;
	}

}