package com.trungtamtienganh.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamtienganh.dto.ScheduleDTO;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.ScheduleRepository;
import com.trungtamtienganh.service.ScheduleService;
import com.trungtamtienganh.utils.MyConstant;
import com.trungtamtienganh.utils.RestConstant;

@RestController
@RequestMapping("/admin/schedules")
@CrossOrigin
public class ScheduleAdminController {

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@PostMapping(value = "", consumes = RestConstant.CONSUMES_JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public ScheduleDTO addClasses(@Valid @RequestBody ScheduleDTO scheduleDTO) {

		scheduleDTO.setId(0);
		return scheduleService.save(scheduleDTO);
	}

	@PutMapping(value = "/{id}", consumes = RestConstant.CONSUMES_JSON)
	public ScheduleDTO updateClasses(@PathVariable("id") Integer id, @Valid @RequestBody ScheduleDTO scheduleDTO) {

		if (id <= 0)
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.SCHEDULE);

		scheduleDTO.setId(id);

//		scheduleRepository.deleteAllByClassesId(id);

		return scheduleService.save(scheduleDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteCourse(@PathVariable("id") Integer id) {

		scheduleService.delete(id);
	}

}
