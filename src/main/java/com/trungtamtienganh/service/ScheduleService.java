package com.trungtamtienganh.service;

import java.util.List;

import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.ScheduleDTO;

public interface ScheduleService {
	PaginationWrapper<List<ScheduleDTO>> getList(Integer classesId,String dateFrom, String dateTo, int page, int size);
	
	PaginationWrapper<List<ScheduleDTO>> getListByUser(Integer userId,String dateFrom, String dateTo, int page, int size);

	ScheduleDTO save(ScheduleDTO scheduleDTO);

	void delete(Integer id);
	
}
