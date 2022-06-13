package com.trungtamtienganh.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamtienganh.dto.ClassesDTO;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.ScheduleDTO;
import com.trungtamtienganh.dto.UserClassesDTO;
import com.trungtamtienganh.dto.UserClassesRequestDTO;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.service.ClassesService;
import com.trungtamtienganh.service.ScheduleService;
import com.trungtamtienganh.utils.MyConstant;
import com.trungtamtienganh.utils.RestConstant;

@RestController
@RequestMapping("/admin/classes")
@CrossOrigin
public class ClassesAdminController {

	@Autowired
	private ClassesService classesService;

	@Autowired
	private ScheduleService scheduleService;

	@PostMapping(value = "", consumes = RestConstant.CONSUMES_JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public ClassesDTO addClasses(@Valid @RequestBody ClassesDTO classesDTO) {

		classesDTO.setId(0);
		return classesService.save(classesDTO);
	}

	@PutMapping(value = "/{id}", consumes = RestConstant.CONSUMES_JSON)
	public ClassesDTO updateClasses(@PathVariable("id") Integer id, @Valid @RequestBody ClassesDTO classesDTO) {

		if (id <= 0)
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.COURSE);

		classesDTO.setId(id);

		return classesService.update(classesDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteCourse(@PathVariable("id") Integer id) {

		classesService.delete(id);
	}

	@GetMapping("/{id}")
	public ClassesDTO getListSchedule(@PathVariable("id") Integer id) {

		return classesService.getById(id);

	}

	@GetMapping("/{id}/schedules")
	public PaginationWrapper<List<ScheduleDTO>> getListSchedule(@PathVariable("id") Integer id,
			@RequestParam(name = "dateFrom", required = false, defaultValue = "") String dateFrom,
			@RequestParam(name = "dateTo", required = false, defaultValue = "") String dateTo,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "12") int size) {

		return scheduleService.getList(id, dateFrom, dateTo, page, size);

	}

	@GetMapping("/register")
	public PaginationWrapper<List<UserClassesDTO>> getListUserRegister(
			@RequestParam(name = "name", required = false, defaultValue = "") String name,
			@RequestParam(name = "status", required = false, defaultValue = "") String status,
			@RequestParam(name = "classId", required = false) Integer classId,
			@RequestParam(name = "levelSlug", required = false, defaultValue = "") String levelSlug,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "12") int size) {

		return classesService.getListUserClass(name, status, classId, levelSlug, page, size);

	}
	
	@PostMapping("/register")
	public UserClassesDTO addUserClasses(@Valid @RequestBody UserClassesRequestDTO userClassesRequestDTO) {
		return classesService.saveUserClasses(userClassesRequestDTO);
	}
	
	@PutMapping("/register")
	public UserClassesDTO updateUserClasses(@Valid @RequestBody UserClassesRequestDTO userClassesRequestDTO) {
		return classesService.saveUserClasses(userClassesRequestDTO);
	}
	
	@DeleteMapping("/register")
	public void deleteUserClasses(@Valid @RequestBody UserClassesRequestDTO userClassesRequestDTO) {
		classesService.deleteUserClass(userClassesRequestDTO);
	}
}
