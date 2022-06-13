package com.trungtamtienganh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trungtamtienganh.entity.CourseWord;
import com.trungtamtienganh.entity.CourseWord_PK;


public interface CourseWordRepository extends JpaRepository<CourseWord, CourseWord_PK> {

	Page<CourseWord> findAllByCourseSlug(String courseSlug, Pageable pageable);
}
