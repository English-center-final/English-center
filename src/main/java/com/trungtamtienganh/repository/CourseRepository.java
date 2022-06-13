package com.trungtamtienganh.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trungtamtienganh.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

	Page<Course> findAllByNameContainingAndTopicSlugContaining(String name, String topicSlug, Pageable pageable);

	Optional<Course> findBySlug(String slug);

	boolean existsBySlug(String slug);

	boolean existsByIdNotAndName(Integer id, String name);
}