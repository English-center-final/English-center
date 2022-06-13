package com.trungtamtienganh.converter;

import org.springframework.stereotype.Component;

import com.trungtamtienganh.dto.TopicDTO;
import com.trungtamtienganh.entity.Topic;
import com.trungtamtienganh.utils.CommonFuc;

@Component
public class TopicConverter {

	public TopicDTO toTopicDTO(Topic topic) {

		TopicDTO topicDTO = new TopicDTO();

		topicDTO.setId(topic.getId());
		topicDTO.setName(topic.getName());
		topicDTO.setSlug(topic.getSlug());

		return topicDTO;
	}

	public Topic toTopic(TopicDTO topicDTO) {

		Topic topic = new Topic();
		topic.setId(topicDTO.getId());

		String name = topicDTO.getName();
		topic.setName(name);
		topic.setSlug(CommonFuc.toSlug(name));

		return topic;
	}
}