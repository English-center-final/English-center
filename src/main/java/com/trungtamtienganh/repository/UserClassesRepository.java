package com.trungtamtienganh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.trungtamtienganh.entity.UserClasses;
import com.trungtamtienganh.entity.UserClasses_PK;

public interface UserClassesRepository extends JpaRepository<UserClasses, UserClasses_PK>, JpaSpecificationExecutor<UserClasses> {
	List<UserClasses> findAllByUserIdAndStatusContaining(Integer userId, String status);
	List<UserClasses> findAllByClassesId(Integer classesId);
	List<UserClasses> findAllByUserUsername(String username);
	
}
