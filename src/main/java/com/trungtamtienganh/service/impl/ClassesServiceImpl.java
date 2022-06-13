package com.trungtamtienganh.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.trungtamtienganh.converter.ClassesConverter;
import com.trungtamtienganh.converter.UserClassesConverter;
import com.trungtamtienganh.dto.ClassesDTO;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.UserClassesDTO;
import com.trungtamtienganh.dto.UserClassesRequestDTO;
import com.trungtamtienganh.dto.UserClassesSubscribeDTO;
import com.trungtamtienganh.entity.Classes;
import com.trungtamtienganh.entity.Schedule;
import com.trungtamtienganh.entity.UserClasses;
import com.trungtamtienganh.entity.UserClasses_PK;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.ClassesRepository;
import com.trungtamtienganh.repository.LevelRepository;
import com.trungtamtienganh.repository.ScheduleRepository;
import com.trungtamtienganh.repository.UserClassesRepository;
import com.trungtamtienganh.service.ClassesService;
import com.trungtamtienganh.utils.AuthenInfo;
import com.trungtamtienganh.utils.DateProcessor;
import com.trungtamtienganh.utils.EntityValidator;
import com.trungtamtienganh.utils.MyConstant;
import com.trungtamtienganh.utils.specification.ClassesSpecification;
import com.trungtamtienganh.utils.specification.UserClassSpecification;

@Service
@Transactional(dontRollbackOn = Exception.class)
public class ClassesServiceImpl implements ClassesService {

	@Autowired
	private ClassesRepository classesRepository;

	@Autowired
	private ClassesConverter classesConverter;

	@Autowired
	private UserClassesConverter userClassesConverter;

	@Autowired
	private LevelRepository levelRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private UserClassesRepository userClassesRepository;

//	@Autowired
//	private AuthenInfo authenInfo;

	@Override
	public PaginationWrapper<List<ClassesDTO>> getList(String levelSlug, Integer branchId, String status,
			String dateFrom, String dateTo, int page, int size) {
		if (levelSlug == null || status == null || dateFrom == null || dateTo == null || page < 0 || size < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Specification<Classes> condition = Specification.where(ClassesSpecification.findByLevelSlug(levelSlug))
				.and(ClassesSpecification.findByBranch(branchId)).and(ClassesSpecification.findByStatus(status))
				.and(ClassesSpecification.findByDateStart(dateFrom)).and(ClassesSpecification.findByDateEnd(dateTo));

		Page<Classes> classesPage = classesRepository.findAll(condition,
				PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateStart")));

		PaginationWrapper<List<ClassesDTO>> result = new PaginationWrapper<>();
		result.setPage(page);
		result.setSize(size);
		result.setTotalPages(classesPage.getTotalPages());

		List<ClassesDTO> data = classesPage.toList().stream().map(c -> classesConverter.toClassesDTO(c))
				.collect(Collectors.toList());
		result.setData(data);

		return result;
	}

	@Override
	public ClassesDTO save(ClassesDTO classesDTO) {
		validate(classesDTO);

		EntityValidator.checkValidate(errors -> {

			if (classesDTO.getDateStart() == null
					|| !DateProcessor.toLocalDate(classesDTO.getDateStart()).isAfter(LocalDate.now())) {
				errors.put("dateStart", "Invalid dateStart");
			}
		});

		Classes classes = classesRepository.save(classesConverter.toClasses(classesDTO));

		int numOfLessons = classes.getNumOfLessons();
		LocalDate dateStart = classes.getDateStart();

		String dateStudy = classesDTO.getDate();

		// Synchronized block
		synchronized (this) {
			if (scheduleRepository.findAllByClassesId(classes.getId()).size() > 0) {
				scheduleRepository.deleteAllByClassesId(classes.getId());
			}
		}

		addSchedule(numOfLessons, dateStart, dateStudy, classesDTO.getRoom(), classesDTO.getSession(),
				classesDTO.getDescription(), classes);

		return classesConverter.toClassesDTO(classes);
	}

	private void validate(ClassesDTO classesDTO) {

		if (classesDTO == null || classesDTO.getId() < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Integer classesId = classesDTO.getId();

		if (classesId != 0 && !classesRepository.existsById(classesId))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.CLASSES);

		if (!levelRepository.existsById(classesDTO.getLevelId()))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.LEVEL);
	}

	@Override
	public ClassesDTO update(ClassesDTO classesDTO) {
		validate(classesDTO);

		Classes classes = classesRepository.save(classesConverter.toClasses(classesDTO));

		int numOfLessons = classes.getNumOfLessons();
		LocalDate dateStart = classes.getDateStart();

		String dateStudy = classesDTO.getDate();

		// Synchronized block
		synchronized (this) {
			if (scheduleRepository.findAllByClassesId(classes.getId()).size() > 0) {
				scheduleRepository.deleteAllByClassesId(classes.getId());
			}
		}

		addSchedule(numOfLessons, dateStart, dateStudy, classesDTO.getRoom(), classesDTO.getSession(),
				classesDTO.getDescription(), classes);

		return classesConverter.toClassesDTO(classes);
	}

	private void addSchedule(int numOfLessons, LocalDate dateStart, String dateStudy, String room, String session,
			String description, Classes classes) {
		int count = 0;
		LocalDate date = dateStart;
		String dayOfWeek;

		do {
			dayOfWeek = date.getDayOfWeek().toString();

			if (dateStudy.contains(dayOfWeek)) {

				Schedule schedule = new Schedule();
				schedule.setDate(date);
				schedule.setRoom(room);
				schedule.setSession(session);
				schedule.setDescription(description);
				schedule.setStatus(true);
				schedule.setClasses(classes);

				scheduleRepository.save(schedule);
				count++;
			}

			date = date.plusDays(1);
		} while (count < numOfLessons);

	}

	@Override
	public void delete(Integer id) {

		if (id == null || id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		classesRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.CLASSES));

		scheduleRepository.deleteAllByClassesId(id);

		classesRepository.deleteById(id);

	}

	@Override
	public UserClassesDTO saveUserClasses(UserClassesRequestDTO requestDTO) {
		UserClasses userClasses = userClassesConverter.toUserClasses(requestDTO);

		userClassesRepository.save(userClasses);
		return userClassesConverter.toUserClassesDTO(userClassesRepository.save(userClasses));
	}

	@Override
	public List<UserClassesSubscribeDTO> getUserClassesByUserId() {
		AuthenInfo.checkLogin();

		List<UserClasses> userClasses = userClassesRepository.findAllByUserUsername(AuthenInfo.getUsername());

		return userClasses.stream().map(u -> {

			List<Schedule> list = scheduleRepository.findAllByClassesId(u.getClasses().getId());

			String session = "1";

			if (list.size() > 0) {
				Schedule schedule = list.get(0);
				session = schedule.getSession();
			}

			return new UserClassesSubscribeDTO(u.getClasses().getId(), u.getStatus(),
					u.getClasses().getLevel().getName(), DateProcessor.toDateString(u.getClasses().getDateStart()),
					u.getClasses().getDate(), session);

		}).collect(Collectors.toList());
	}

	@Override
	public PaginationWrapper<List<UserClassesDTO>> getListUserClass(String name, String status, Integer classId,
			String levelSlug, int page, int size) {
		if (name == null || status == null || levelSlug == null || page < 0 || size < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Specification<UserClasses> condition = Specification.where(UserClassSpecification.findByUserName(name))
				.and(UserClassSpecification.findByStatus(status)).and(UserClassSpecification.findByClassesId(classId))
				.and(UserClassSpecification.findByLevelSlug(levelSlug));

		Page<UserClasses> userClassesPage = userClassesRepository.findAll(condition,
				PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "status")));

		PaginationWrapper<List<UserClassesDTO>> result = new PaginationWrapper<>();
		result.setPage(page);
		result.setSize(size);
		result.setTotalPages(userClassesPage.getTotalPages());

		List<UserClassesDTO> data = userClassesPage.toList().stream().map(c -> userClassesConverter.toUserClassesDTO(c))
				.collect(Collectors.toList());
		result.setData(data);

		return result;
	}

	@Override
	public ClassesDTO getById(Integer id) {

		if (id == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Classes classes = classesRepository.getById(id);

		return classesConverter.toClassesDTO(classes);
	}

	@Override
	public void deleteUserClass(UserClassesRequestDTO requestDTO) {
		Integer classesId = requestDTO.getClassesId();
		Integer userId = requestDTO.getUserId(); 
		if (classesId == null || classesId <= 0 || userId == null || userId <=0)
			throw MyExceptionHelper.throwIllegalArgumentException();
		
		UserClasses_PK classes_PK = new UserClasses_PK(userId, classesId);
		userClassesRepository.deleteById(classes_PK);
	}

}
