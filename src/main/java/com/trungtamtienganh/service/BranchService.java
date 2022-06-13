package com.trungtamtienganh.service;

import java.util.List;

import com.trungtamtienganh.entity.Branch;

public interface BranchService {
	List<Branch> getList(String name);

	Branch save(Branch branch);

	void delete(Integer id);

}
