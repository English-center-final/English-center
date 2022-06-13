package com.trungtamtienganh.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import com.trungtamtienganh.entity.Schedule;

@Transactional
@Component
public interface ScheduleRepository extends JpaRepository<Schedule, Integer>, JpaSpecificationExecutor<Schedule> {

	void deleteAllByClassesId(Integer id);
	List<Schedule> findAllByClassesId(Integer classesI);
}