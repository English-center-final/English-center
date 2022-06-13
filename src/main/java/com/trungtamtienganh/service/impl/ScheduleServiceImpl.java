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

import com.trungtamtienganh.converter.ScheduleConverter;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.ScheduleDTO;
import com.trungtamtienganh.entity.Schedule;
import com.trungtamtienganh.entity.UserClasses;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.ClassesRepository;
import com.trungtamtienganh.repository.ScheduleRepository;
import com.trungtamtienganh.repository.UserClassesRepository;
import com.trungtamtienganh.service.ScheduleService;
import com.trungtamtienganh.utils.DateProcessor;
import com.trungtamtienganh.utils.MyConstant;
import com.trungtamtienganh.utils.specification.ScheduleSpecification;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private ClassesRepository classesRepository;

	@Autowired
	private UserClassesRepository userClassesRepository;

	@Autowired
	private ScheduleConverter scheduleConverter;

	@Override
	public PaginationWrapper<List<ScheduleDTO>> getList(Integer classesId, String dateFrom, String dateTo, int page,
			int size) {
		if (classesId == null || dateFrom == null || dateTo == null || page < 0 || size < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Specification<Schedule> condition = Specification
				.where(ScheduleSpecification.findByClassesId(classesId))
				.and(ScheduleSpecification.findByDateStart(dateFrom))
				.and(ScheduleSpecification.findByDateEnd(dateTo));

		Page<Schedule> schedulePage = scheduleRepository.findAll(condition,
				PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "date")));

		PaginationWrapper<List<ScheduleDTO>> result = new PaginationWrapper<>();
		result.setPage(page);
		result.setSize(size);
		result.setTotalPages(schedulePage.getTotalPages());

		List<ScheduleDTO> data = schedulePage.toList().stream().map(c -> scheduleConverter.toScheduleDTO(c))
				.collect(Collectors.toList());
		result.setData(data);

		return result;
	}

	@Override
	public ScheduleDTO save(ScheduleDTO scheduleDTO) {
		validate(scheduleDTO);
		Schedule schedule = scheduleRepository.save(scheduleConverter.toSchedule(scheduleDTO));

		return scheduleConverter.toScheduleDTO(schedule);
	}

	private void validate(ScheduleDTO scheduleDTO) {

		if (scheduleDTO == null || scheduleDTO.getId() < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Integer scheduleId = scheduleDTO.getId();

		if (scheduleId != 0 && !scheduleRepository.existsById(scheduleId))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.SCHEDULE);

		if (!classesRepository.existsById(scheduleDTO.getClassId()))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.CLASSES);

	}

	@Override
	public void delete(Integer id) {
		if (id == null || id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		scheduleRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.SCHEDULE));
		scheduleRepository.deleteById(id);

	}

	@Override
	public PaginationWrapper<List<ScheduleDTO>> getListByUser(Integer userId, String dateFrom, String dateTo, int page,
			int size) {
		
		if (userId == null || dateFrom == null || dateTo == null || page < 0 || size < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();
		
		List<UserClasses> userClasses = userClassesRepository.findAllByUserIdAndStatusContaining(userId, MyConstant.ACCEPT);
		List<Integer> classesIds = userClasses.stream().map(e -> e.getClasses().getId()).collect(Collectors.toList());
		
		PaginationWrapper<List<ScheduleDTO>> result = new PaginationWrapper<>();
		result.setPage(page);
		result.setSize(size);
		
		if(classesIds == null ||classesIds.size() == 0){
			result.setTotalPages(0);
			return result;
		}
		
		if(dateFrom.equals("") && dateTo.equals("")) {
			dateFrom = DateProcessor.toDateString(LocalDate.now());
		}
		
		
		Specification<Schedule> condition = Specification
				.where(ScheduleSpecification.findByClassesId(classesIds))
				.and(ScheduleSpecification.findByDateStart(dateFrom))
				.and(ScheduleSpecification.findByDateEnd(dateTo));

		Page<Schedule> schedulePage = scheduleRepository.findAll(condition,
				PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "date")));
		
		result.setTotalPages(schedulePage.getTotalPages());

		List<ScheduleDTO> data = schedulePage.toList().stream().map(c -> scheduleConverter.toScheduleDTO(c))
				.collect(Collectors.toList());
		result.setData(data);
		
		
		return result;
	}

}
