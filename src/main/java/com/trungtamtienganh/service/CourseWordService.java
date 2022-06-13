package com.trungtamtienganh.service;

import java.util.List;

import com.trungtamtienganh.dto.PaginationWrapper;
import com.trungtamtienganh.dto.WordDTO;

public interface CourseWordService {

	PaginationWrapper<List<WordDTO>> getList(String courseSlug, int page, int size);
}
