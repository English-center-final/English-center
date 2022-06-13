package com.trungtamtienganh.converter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trungtamtienganh.dto.ClassesDTO;
import com.trungtamtienganh.entity.Branch;
import com.trungtamtienganh.entity.ClassStatus;
import com.trungtamtienganh.entity.Classes;
import com.trungtamtienganh.entity.Level;
import com.trungtamtienganh.entity.Schedule;
import com.trungtamtienganh.repository.BranchRepository;
import com.trungtamtienganh.repository.ClassesRepository;
import com.trungtamtienganh.repository.LevelRepository;
import com.trungtamtienganh.repository.ScheduleRepository;
import com.trungtamtienganh.repository.UserClassesRepository;
import com.trungtamtienganh.utils.DateProcessor;

@Component
public class ClassesConverter {

	@Autowired
	private ClassesRepository classesRepository;

	@Autowired
	private UserClassesRepository userClassesRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private LevelRepository levelRepository;
	
	@Autowired
	private BranchRepository branchRepository;

	public ClassesDTO toClassesDTO(Classes classes) {
		ClassesDTO classesDTO = new ClassesDTO();
		
		Branch branch = classes.getBranch();
		
		if(branch == null) {
			branch = new Branch();
		}
		
		classesDTO.setId(classes.getId());
		classesDTO.setAmount(classes.getAmount());
		classesDTO.setDateStart(DateProcessor.toDateString(classes.getDateStart()));
		classesDTO.setNumOfLessons(classes.getNumOfLessons());
		classesDTO.setStatus(classes.getStatus().toString());
		classesDTO.setDate(classes.getDate());
		classesDTO.setLevelId(classes.getLevel().getId());
		classesDTO.setLevelName(classes.getLevel().getName());
		classesDTO.setBranchId(branch.getId());
		classesDTO.setBranchName(branch.getName());
		classesDTO.setBranchAddress(branch.getAddress());

		List<Schedule> list = scheduleRepository.findAllByClassesId(classes.getId());

		if (list.size() > 0) {
			Schedule schedule = list.get(0);
			classesDTO.setDescription(schedule.getDescription());
			classesDTO.setRoom(schedule.getRoom());
			classesDTO.setSession(schedule.getSession());
		}

		int numOfRegister = userClassesRepository.findAllByClassesId(classes.getId()).size();
		classesDTO.setNumOfRegister(numOfRegister);

		return classesDTO;
	}

	public Classes toClasses(ClassesDTO classesDTO) {
		Integer id = classesDTO.getId();

		Classes classesResult = classesRepository.findById(id).orElse(new Classes(0));
		classesResult.setId(classesDTO.getId());

		classesResult.setAmount(classesDTO.getAmount());
		classesResult.setDateStart(DateProcessor.toLocalDate(classesDTO.getDateStart()));
		classesResult.setNumOfLessons(classesDTO.getNumOfLessons());
		classesResult.setStatus(ClassStatus.valueOf(classesDTO.getStatus()));
//		classesResult.setDescription(classesDTO.getDescription());
		classesResult.setDate(classesDTO.getDate());

		Level level = levelRepository.findById(classesDTO.getLevelId()).orElse(new Level(0));
		Branch branch = branchRepository.findById(classesDTO.getBranchId()).orElse(new Branch(0));
		classesResult.setLevel(level);
		classesResult.setBranch(branch);;

		return classesResult;
	}
}
