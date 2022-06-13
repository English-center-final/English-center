package com.trungtamtienganh.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trungtamtienganh.dto.ScheduleDTO;
import com.trungtamtienganh.entity.Classes;
import com.trungtamtienganh.entity.Schedule;
import com.trungtamtienganh.repository.ClassesRepository;
import com.trungtamtienganh.repository.ScheduleRepository;
import com.trungtamtienganh.utils.DateProcessor;

@Component
public class ScheduleConverter {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private ClassesRepository classesRepository;

	public ScheduleDTO toScheduleDTO(Schedule schedule) {
		ScheduleDTO scheduleDTO = new ScheduleDTO();

		scheduleDTO.setId(schedule.getId());
		scheduleDTO.setDate(DateProcessor.toDateString(schedule.getDate()));
		scheduleDTO.setDescription(schedule.getDescription());
		scheduleDTO.setRoom(schedule.getRoom());
		scheduleDTO.setSession(schedule.getSession());
		scheduleDTO.setStatus(schedule.isStatus());
		scheduleDTO.setClassId(schedule.getClasses().getId());

		return scheduleDTO;
	}

	public Schedule toSchedule(ScheduleDTO scheduleDTO) {
		Integer id = scheduleDTO.getId();

		Schedule scheduleResult = scheduleRepository.findById(id).orElse(new Schedule(0));
		scheduleResult.setId(scheduleDTO.getId());
		scheduleResult.setDate(DateProcessor.toLocalDate(scheduleDTO.getDate()));
		scheduleResult.setDescription(scheduleDTO.getDescription());
		scheduleResult.setRoom(scheduleDTO.getRoom());
		scheduleResult.setSession(scheduleDTO.getSession());
		scheduleResult.setStatus(scheduleDTO.isStatus());

		Classes classes = classesRepository.findById(scheduleDTO.getClassId()).orElse(new Classes(0));
		scheduleResult.setClasses(classes);

		return scheduleResult;
	}
}
