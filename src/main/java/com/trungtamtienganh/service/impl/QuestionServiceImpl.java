package com.trungtamtienganh.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.converter.ExamConverter;
import com.trungtamtienganh.converter.QuestionConverter;
import com.trungtamtienganh.dto.Part3_4_6_7GroupDTO;
import com.trungtamtienganh.dto.QuestionDTO;
import com.trungtamtienganh.entity.Audio;
import com.trungtamtienganh.entity.Paragraph;
import com.trungtamtienganh.entity.Question;
import com.trungtamtienganh.entity.QuestionParagraph;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.AudioRepository;
import com.trungtamtienganh.repository.ExamRepository;
import com.trungtamtienganh.repository.ParagraphRepository;
import com.trungtamtienganh.repository.QuestionRepository;
import com.trungtamtienganh.service.AwsS3Service;
import com.trungtamtienganh.service.QuestionService;
import com.trungtamtienganh.utils.ExamUtils;
import com.trungtamtienganh.utils.MyConstant;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuestionConverter questionConverter;

	@Autowired
	private ExamRepository examRepository;

	@Autowired
	private ParagraphRepository paragraphRepository;

	@Autowired
	private ExamConverter examConverter;

	@Autowired
	private AwsS3Service awsS3Service;

	@Autowired
	private AudioRepository audioRepository;

	@Override
	public List<QuestionDTO> getList(int type, int examId) {

		if (type != 1 && type != 2 && type != 5)
			throw MyExceptionHelper.throwIllegalArgumentException();

		checkExamExists(examId);

		List<Question> questionns = questionRepository.findAllByTypeAndExamIdOrderByStt(type, examId);

		return questionns.stream().map(questionEle -> questionConverter.toQuestionDTO(questionEle))
				.collect(Collectors.toList());
	}

	@Override
	public List<Part3_4_6_7GroupDTO> getListGroup(int type, int examId) {

		if (type != 3 && type != 4 && type != 6 && type != 7)
			throw MyExceptionHelper.throwIllegalArgumentException();

		checkExamExists(examId);

		List<Paragraph> paragraphs = paragraphRepository.findAllByExamIdAndQuestionType(examId, type);

		return paragraphs.stream().map(paragraphEle -> examConverter.toPart3_4_6_7GroupDTO(paragraphEle))
				.collect(Collectors.toList());
	}

	private void checkExamExists(int examId) {

		if (examId <= 0 || !examRepository.existsById(examId))
			MyExceptionHelper.throwResourceNotFoundException(MyConstant.EXAM);
	}

	@Override
	public QuestionDTO save(QuestionDTO questionDTO) {

		validate(questionDTO);

		Question question = questionRepository.save(questionConverter.toQuestion(questionDTO));

		return questionConverter.toQuestionDTO(question);
	}

	private void validate(QuestionDTO questionDTO) {

		if (questionDTO == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		int id = questionDTO.getId();

		if (!questionRepository.existsById(id))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.QUESTION);
	}

	@Override
	public void createQuestions(int examId, int[] stts) {

		if (stts == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		if (!examRepository.existsById(examId))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.EXAM);

		// part 1
		saveEmptyQuestionsOfPart1_2_5(1, examId);
		// part2
		saveEmptyQuestionsOfPart1_2_5(2, examId);
		// part3
		saveEmptyQuestionPart3_4(3, examId);
		// part4
		saveEmptyQuestionPart3_4(4, examId);
		// part5
		saveEmptyQuestionsOfPart1_2_5(5, examId);
		// part6
		saveEmptyQuestionPart6(examId);
		// part7
		saveEmptyQuestionPart7(examId, stts);

	}

	@Override
	public String uploadAudio(int id, MultipartFile audioFile) {

		if (id <= 0 || audioFile == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Question question = questionRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.QUESTION));

		int type = question.getType();

		if (type != 1 && type != 2)
			MyExceptionHelper.throwResourceNotFoundException(MyConstant.QUESTION);

		Audio audio = question.getAudio();

		if (audio == null) {
			audio = new Audio();
		}

		else {
			if (audio.getName() != null) {
				awsS3Service.deleteObjectFromUrl(audio.getName());
			}
		}

		String audioString = awsS3Service.uploadObject(audioFile);
		audio.setName(audioString);
		audio.setQuestion(question);
		audioRepository.save(audio);

		return audio.getName();
	}

	private void saveEmptyQuestionsOfPart1_2_5(int type, int examId) {

		int start = 0;
		int end = 0;

		if (type == 1) {
			start = MyConstant.PART1_START_STT;
			end = MyConstant.PART1_END_STT;
		}

		if (type == 2) {
			start = MyConstant.PART2_START_STT;
			end = MyConstant.PART2_END_STT;
		}

		if (type == 5) {
			start = MyConstant.PART5_START_STT;
			end = MyConstant.PART5_END_STT;
		}

		List<Question> questions = new ArrayList<>();
		for (int i = start; i <= end; i++) {

			Question emptyQuestion = ExamUtils.createQuestionEmpty(i, type, examId);
			questions.add(emptyQuestion);
		}

		questionRepository.saveAll(questions);

	}

	private void saveEmptyQuestionPart3_4(int type, int examId) {

		int start = 0;
		int end = 0;

		if (type == 3) {
			start = MyConstant.PART3_START_STT;
			end = MyConstant.PART3_END_STT;
		}

		if (type == 4) {
			start = MyConstant.PART4_START_STT;
			end = MyConstant.PART4_END_STT;
		}

		for (int i = start; i <= end; i = i + 3) {

			Question question1 = questionRepository.save(ExamUtils.createQuestionEmpty(i, type, examId));
			Question question2 = questionRepository.save(ExamUtils.createQuestionEmpty(i + 1, type, examId));
			Question question3 = questionRepository.save(ExamUtils.createQuestionEmpty(i + 2, type, examId));

			Paragraph paragraph = new Paragraph();
			paragraph.setImage("");
			paragraph.setContent("");
			paragraph.setTranscript("");

			List<QuestionParagraph> questionParagraphs = new ArrayList<>();
			questionParagraphs.add(new QuestionParagraph(new Question(question1.getId()), paragraph));
			questionParagraphs.add(new QuestionParagraph(new Question(question2.getId()), paragraph));
			questionParagraphs.add(new QuestionParagraph(new Question(question3.getId()), paragraph));

			paragraph.setQuestionParagraphs(questionParagraphs);

			paragraphRepository.save(paragraph);

		}
	}

	private void saveEmptyQuestionPart6(int examId) {

		for (int i = MyConstant.PART6_START_STT; i <= MyConstant.PART6_END_STT; i = i + 4) {

			Question question1 = questionRepository.save(ExamUtils.createQuestionEmpty(i, 6, examId));
			Question question2 = questionRepository.save(ExamUtils.createQuestionEmpty(i + 1, 6, examId));
			Question question3 = questionRepository.save(ExamUtils.createQuestionEmpty(i + 2, 6, examId));
			Question question4 = questionRepository.save(ExamUtils.createQuestionEmpty(i + 3, 6, examId));

			Paragraph paragraph = new Paragraph();
			paragraph.setImage("");
			paragraph.setContent("");
			paragraph.setTranscript("");

			List<QuestionParagraph> questionParagraphs = new ArrayList<>();
			questionParagraphs.add(new QuestionParagraph(new Question(question1.getId()), paragraph));
			questionParagraphs.add(new QuestionParagraph(new Question(question2.getId()), paragraph));
			questionParagraphs.add(new QuestionParagraph(new Question(question3.getId()), paragraph));
			questionParagraphs.add(new QuestionParagraph(new Question(question4.getId()), paragraph));

			paragraph.setQuestionParagraphs(questionParagraphs);

			paragraphRepository.save(paragraph);

		}
	}

	private void saveEmptyQuestionPart7(int examId, int[] stts) {

		int length = stts.length;

		for (int i = 0; i < length; i++) {

			int start = stts[i];
			int end = i == length - 1 ? 201 : stts[i + 1];

			Paragraph paragraph = new Paragraph();
			paragraph.setImage("");
			paragraph.setContent("");
			paragraph.setTranscript("");
			List<QuestionParagraph> questionParagraphs = new ArrayList<>();

			for (int j = start; j < end; j++) {

				Question question = questionRepository.save(ExamUtils.createQuestionEmpty(j, 7, examId));
				questionParagraphs.add(new QuestionParagraph(new Question(question.getId()), paragraph));
			}

			paragraph.setQuestionParagraphs(questionParagraphs);

			paragraphRepository.save(paragraph);

		}
	}

	@Override
	public String uploadImageForPart1(int id, MultipartFile imageFile) {

		if (id <= 0 || imageFile == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Question question = questionRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.QUESTION));

		int type = question.getType();
		if (type != 1)
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.QUESTION);

		String image = question.getContent();

		if (image != null) {
			awsS3Service.deleteObjectFromUrl(image);
		}

		String imageUpload = awsS3Service.uploadObject(imageFile);

		question.setContent(imageUpload);
		questionRepository.save(question);

		return imageUpload;
	}
}