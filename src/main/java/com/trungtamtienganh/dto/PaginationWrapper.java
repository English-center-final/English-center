package com.trungtamtienganh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationWrapper<T extends Object> {

	private T data;
	private int page;
	private int size;
	private int totalPages;

}
