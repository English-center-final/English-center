package com.trungtamtienganh.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.trungtamtienganh.repository.TopicRepository;


public class TopicIdExistsValidator implements ConstraintValidator<TopicIdExists, Integer> {

	@Autowired
	private TopicRepository topicRepository;

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {

		if (value == null || value <= 0)
			return false;

		return topicRepository.existsById(value);
	}
}