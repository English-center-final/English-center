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

import com.trungtamtienganh.dto.TopicDTO;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.service.TopicService;
import com.trungtamtienganh.utils.MyConstant;
import com.trungtamtienganh.utils.RestConstant;

@RestController
@RequestMapping("/admin/courses/topics")
@CrossOrigin
public class TopicAdminController {

	@Autowired
	private TopicService topicService;

	@PostMapping(value = "", consumes = RestConstant.CONSUMES_JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public TopicDTO addTopic(@Valid @RequestBody TopicDTO topicDTO) {

		topicDTO.setId(0);
		return topicService.save(topicDTO);
	}

	@PutMapping(value = "/{id}", consumes = RestConstant.CONSUMES_JSON)
	public TopicDTO updateTopic(@PathVariable("id") Integer id, @Valid @RequestBody TopicDTO topicDTO) {

		if (id <= 0)
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.TOPIC);

		topicDTO.setId(id);
		return topicService.save(topicDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteTopic(@PathVariable("id") Integer id) {

		topicService.delete(id);
	}

}
