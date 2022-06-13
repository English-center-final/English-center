package com.trungtamtienganh.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamtienganh.dto.ClassesDTO;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.ScheduleDTO;
import com.trungtamtienganh.dto.UserClassesDTO;
import com.trungtamtienganh.dto.UserClassesRequestDTO;
import com.trungtamtienganh.entity.User;
import com.trungtamtienganh.service.ClassesService;
import com.trungtamtienganh.service.ScheduleService;
import com.trungtamtienganh.utils.AuthenInfo;
import com.trungtamtienganh.utils.MyConstant;

@RestController
@RequestMapping("/classes")
@CrossOrigin
public class ClassesController {

	@Autowired
	private ClassesService classesService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private AuthenInfo authenInfo;

	@GetMapping("")
	public PaginationWrapper<List<ClassesDTO>> getListClasses(

			@RequestParam(name = "levelSlug", required = false, defaultValue = "") String levelSlug,
			@RequestParam(name = "branchId", required = false) Integer branchId,
			@RequestParam(name = "status", required = false, defaultValue = "") String status,
			@RequestParam(name = "dateFrom", required = false, defaultValue = "") String dateFrom,
			@RequestParam(name = "dateTo", required = false, defaultValue = "") String dateTo,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "12") int size) {

		return classesService.getList(levelSlug, branchId, status, dateFrom, dateTo, page, size);

	}

	@PostMapping("/registry")
	public UserClassesDTO registyClasses(@Valid @RequestBody UserClassesRequestDTO requestDTO) {

		AuthenInfo.checkLogin();
		User user = authenInfo.getUser();
		requestDTO.setUserId(user.getId());
		requestDTO.setStatus(MyConstant.NEW);

		return classesService.saveUserClasses(requestDTO);
	}
	
	@PutMapping("/cancel")
	public UserClassesDTO cancelClasses(@Valid @RequestBody UserClassesRequestDTO requestDTO) {

		AuthenInfo.checkLogin();
		User user = authenInfo.getUser();
		requestDTO.setUserId(user.getId());
		requestDTO.setStatus(MyConstant.CANCEL);

		return classesService.saveUserClasses(requestDTO);
	}

	@GetMapping("/schedules")
	public PaginationWrapper<List<ScheduleDTO>> getListSchedule(
			@RequestParam(name = "dateFrom", required = false, defaultValue = "") String dateFrom,
			@RequestParam(name = "dateTo", required = false, defaultValue = "") String dateTo,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "12") int size) {

		AuthenInfo.checkLogin();
		User user = authenInfo.getUser();
		
		return scheduleService.getListByUser(user.getId(), dateFrom, dateTo, page, size);
	}
}