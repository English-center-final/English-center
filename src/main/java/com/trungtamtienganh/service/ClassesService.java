package com.trungtamtienganh.service;

import java.util.List;

import com.trungtamtienganh.dto.ClassesDTO;
import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.UserClassesDTO;
import com.trungtamtienganh.dto.UserClassesRequestDTO;
import com.trungtamtienganh.dto.UserClassesSubscribeDTO;

public interface ClassesService {
	PaginationWrapper<List<ClassesDTO>> getList(String levelSlug, Integer branchId, String status, String dateFrom,
			String dateTo, int page, int size);

	ClassesDTO save(ClassesDTO classesDTO);
	
	ClassesDTO update(ClassesDTO classesDTO);

	ClassesDTO getById(Integer id);

	void delete(Integer id);

	UserClassesDTO saveUserClasses(UserClassesRequestDTO requestDTO);

	List<UserClassesSubscribeDTO> getUserClassesByUserId();

	PaginationWrapper<List<UserClassesDTO>> getListUserClass(String name, String status, Integer classId,
			String levelSlug, int page, int size);

	void deleteUserClass(UserClassesRequestDTO requestDTO);
	
}
