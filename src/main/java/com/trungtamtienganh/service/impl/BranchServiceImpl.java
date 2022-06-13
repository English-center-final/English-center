package com.trungtamtienganh.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trungtamtienganh.entity.Branch;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.BranchRepository;
import com.trungtamtienganh.service.BranchService;
import com.trungtamtienganh.utils.MyConstant;

@Service
@Transactional
public class BranchServiceImpl implements BranchService {

	@Autowired
	private BranchRepository branchRepository;

	@Override
	public List<Branch> getList(String name) {
		if (name == null)
			throw MyExceptionHelper.throwIllegalArgumentException();
		List<Branch> branchs = branchRepository.findAllByNameContaining(name);

		return branchs;
	}

	@Override
	public Branch save(Branch branch) {
		if (branch == null || branch.getId() < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Branch branchWasSave = branchRepository.save(branch);

		return branchWasSave;
	}

	@Override
	public void delete(Integer id) {
		if (id == null || id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		branchRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.BRANCH));

		branchRepository.deleteById(id);
	}

}
