package com.trungtamtienganh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trungtamtienganh.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

	boolean existsByIdNotAndName(Integer id, String name);
}
