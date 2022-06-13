package com.trungtamtienganh.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.converter.ExamConverter;
import com.trungtamtienganh.converter.QuestionConverter;
import com.trungtamtienganh.converter.UserExamConverter;
import com.trungtamtienganh.dto.ExamDTO;
import com.trungtamtienganh.dto.ExamQuestionDTO;
import com.trungtamtienganh.dto.ExamResultDTO;
import com.trungtamtienganh.dto.NameSlugDTO;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.UserExamDTO;
import com.trungtamtienganh.entity.Exam;
import com.trungtamtienganh.entity.User;
import com.trungtamtienganh.entity.UserExam;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.BookRepository;
import com.trungtamtienganh.repository.ExamRepository;
import com.trungtamtienganh.repository.ParagraphRepository;
import com.trungtamtienganh.repository.QuestionRepository;
import com.trungtamtienganh.repository.UserExamRepository;
import com.trungtamtienganh.repository.UserRepository;
import com.trungtamtienganh.service.AwsS3Service;
import com.trungtamtienganh.service.ExamService;
import com.trungtamtienganh.service.QuestionService;
import com.trungtamtienganh.utils.AuthenInfo;
import com.trungtamtienganh.utils.EntityValidator;
import com.trungtamtienganh.utils.MyConstant;
import com.trungtamtienganh.utils.MyConsumer;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private ExamConverter examConverter;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuestionConverter questionConverter;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private UserExamConverter userExamConverter;

	@Autowired
	private ParagraphRepository paragraphRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserExamRepository userExamRepository;

	@Autowired
	private AwsS3Service awsS3Service;

	@Override
	public ExamQuestionDTO getExamQuestion(String slug) {

		if (slug == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Exam exam = examRepository.findBySlug(slug)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.EXAM));

		return examConverter.toExamQuestionDTO(exam);
	}

	@Override
	public ExamResultDTO getExamResult(String slug, Map<Integer, String> answers) {

		if (slug == null || answers == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Exam exam = examRepository.findBySlug(slug)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.EXAM));

		ExamResultDTO examResultDTO = examConverter.toExamResultDTO(exam, answers);

		saveUserExam(exam, examResultDTO);

		return examResultDTO;
	}

	public void saveUserExam(Exam exam, ExamResultDTO examResultDTO) {
		UserExam userExam = new UserExam();

//		System.out.println("AuthenInfo.getUsername(): "+ AuthenInfo.getUsername() );
		User user = userRepository.findByUsername(AuthenInfo.getUsername())
				.orElseThrow(() -> MyExceptionHelper.throwAuthenticationException());

		String partResult = examResultDTO.getPart1Number() + ", " + examResultDTO.getPart2Number() + ", "
				+ examResultDTO.getPart3Number() + ", " + examResultDTO.getPart4Number() + ", "
				+ examResultDTO.getPart5Number() + ", " + examResultDTO.getPart6Number() + ", "
				+ examResultDTO.getPart7Number();
		userExam.setId(0);
		userExam.setExam(exam);
		userExam.setUser(user);
		userExam.setListenPoint(examResultDTO.getListenPoint());
		userExam.setReadPoint(examResultDTO.getReadPoint());
		userExam.setTestDate(LocalDateTime.now());
		userExam.setPartResult(partResult);

		userExamRepository.save(userExam);

	}

	@Override
	public List<NameSlugDTO> getListNameSlugs() {

		return examRepository.findAll().stream().map(examEle -> new NameSlugDTO(examEle.getName(), examEle.getSlug()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Object> getQuestionsOfPart(String slug, int type) {

		if (slug == null || type < 1 || type > 7)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Exam exam = examRepository.findBySlug(slug)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.EXAM));
		Integer examId = exam.getId();

		if (type == 1 || type == 2 || type == 5) {

			return questionRepository.findAllByTypeAndExamIdOrderByStt(type, examId).stream()
					.map(questionEle -> questionConverter.toQuestionDTO(questionEle)).collect(Collectors.toList());
		}

		return paragraphRepository.findAllByExamIdAndQuestionType(examId, type).stream()
				.map(paragraphEle -> examConverter.toPart3_4_6_7GroupDTO(paragraphEle)).collect(Collectors.toList());
	}

	@Override
	public PaginationWrapper<List<ExamDTO>> getList(String name, String bookName, int page, int size) {

		if (name == null || bookName == null || page < 0 || size <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Page<Exam> examsPage = examRepository.findAllByNameContainingAndBookNameContaining(name, bookName,
				PageRequest.of(page, size));

		var examDTOsPageResult = new PaginationWrapper<List<ExamDTO>>();
		examDTOsPageResult.setPage(page);
		examDTOsPageResult.setSize(page);
		examDTOsPageResult.setTotalPages(examsPage.getTotalPages());

		List<ExamDTO> examDTOs = examsPage.toList().stream().map(examEle -> examConverter.toExamDTO(examEle))
				.collect(Collectors.toList());
		examDTOsPageResult.setData(examDTOs);

		return examDTOsPageResult;
	}

	@Override
	public ExamDTO save(ExamDTO examDTO) {

		validate(examDTO);

		Exam exam = examRepository.save(examConverter.toExam(examDTO));

		return examConverter.toExamDTO(exam);
	}

	@Override
	public ExamDTO add(ExamDTO examDTO, int[] stts) {

		ExamDTO examDTOResult = save(examDTO);

		stts[0] = MyConstant.PART7_START_STT;
		questionService.createQuestions(examDTOResult.getId(), stts);

		return examDTOResult;
	}

	private void validate(ExamDTO examDTO) {

		if (examDTO == null || examDTO.getId() == null || examDTO.getId() < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Integer id = examDTO.getId();
		if (id != 0 && !examRepository.existsById(id))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.EXAM);

		EntityValidator.checkValidate(errors -> {

			if (examRepository.existsByIdNotAndName(id, examDTO.getName()))
				errors.put("name", "Tên Exam đã trùng");

			if (!bookRepository.existsById(examDTO.getBookId()))
				errors.put("bookId", "Book không tồn tại");

		});

	}

	@Override
	public void delete(Integer id) {

		if (id == null || id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		if (!examRepository.existsById(id))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.EXAM);

		paragraphRepository.deleteAllById(paragraphRepository.getIds(id));
		examRepository.deleteById(id);

	}

	@Override
	public void uploadAudio(int id, MultipartFile part1Audio, MultipartFile part2Audio, MultipartFile part3Audio,
			MultipartFile part4Audio) {

		if (id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Exam exam = examRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.EXAM));

		// part1
//		uploadAudio(part1Audio, exam.getPart1Audio(), (audioUpload) -> {
//
//			exam.setPart1Audio(audioUpload);
//		});
//
//		uploadAudio(part2Audio, exam.getPart2Audio(), (audioUpload) -> {
//
//			exam.setPart2Audio(audioUpload);
//		});
//
//		uploadAudio(part3Audio, exam.getPart3Audio(), (audioUpload) -> {
//
//			exam.setPart3Audio(audioUpload);
//		});
//
//		uploadAudio(part4Audio, exam.getPart4Audio(), (audioUpload) -> {
//
//			exam.setPart4Audio(audioUpload);
//		});

	}

	public void uploadAudio(MultipartFile audio, String audioString, MyConsumer<String> myConsumer) {

		if (audio == null)
			return;

		if (audioString != null) {
			awsS3Service.deleteObjectFromUrl(audioString);
		}

		String audioUpload = awsS3Service.uploadObject(audio);

		myConsumer.handle(audioUpload);
	}

	@Override
	public PaginationWrapper<List<UserExamDTO>> getListUserExam(int page, int size) {

		String username = AuthenInfo.getUsername();

		if (username == null || username.equals(""))
			throw MyExceptionHelper.throwAuthenticationException();

		if (page < 0 || size <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Page<UserExam> userExamsPage = userExamRepository.findAllByUserUsernameContaining(username,
				PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "testDate")));

		var examDTOsPageResult = new PaginationWrapper<List<UserExamDTO>>();
		examDTOsPageResult.setPage(page);
		examDTOsPageResult.setSize(page);
		examDTOsPageResult.setTotalPages(userExamsPage.getTotalPages());

		List<UserExamDTO> userExamDTOs = userExamsPage.toList().stream()
				.map(userExamEle -> userExamConverter.toUserExamDTO(userExamEle)).collect(Collectors.toList());
		examDTOsPageResult.setData(userExamDTOs);

		return examDTOsPageResult;
	}

}
