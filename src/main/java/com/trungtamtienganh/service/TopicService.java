package com.trungtamtienganh.service;

import java.util.List;

import com.trungtamtienganh.dto.TopicDTO;

public interface TopicService {

	List<TopicDTO> getAll();

	TopicDTO save(TopicDTO topicDTO);

	void delete(Integer id);
}
