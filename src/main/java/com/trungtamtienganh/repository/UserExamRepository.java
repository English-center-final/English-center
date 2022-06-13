package com.trungtamtienganh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trungtamtienganh.entity.UserExam;
import com.trungtamtienganh.entity.UserExam_PK;

public interface UserExamRepository extends JpaRepository<UserExam, Integer> {
	Page<UserExam> findAllByUserUsernameContaining(String username, Pageable pageable);

}
