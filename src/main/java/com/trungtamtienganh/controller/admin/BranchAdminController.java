package com.trungtamtienganh.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamtienganh.entity.Branch;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.service.BranchService;
import com.trungtamtienganh.utils.MyConstant;
import com.trungtamtienganh.utils.RestConstant;

@RestController
@RequestMapping("/admin/branches")
@CrossOrigin
public class BranchAdminController {

	@Autowired
	private BranchService branchService;

	@PostMapping(value = "", consumes = RestConstant.CONSUMES_JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Branch addLevel(@Valid @RequestBody Branch branch) {

		branch.setId(0);
		return branchService.save(branch);
	}

	@PutMapping(value = "/{id}", consumes = RestConstant.CONSUMES_JSON)
	public Branch updateLevel(@PathVariable("id") Integer id, @Valid @RequestBody Branch branch) {

		if (id <= 0)
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.BRANCH);

		branch.setId(id);
		return branchService.save(branch);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteLevel(@PathVariable("id") Integer id) {

		branchService.delete(id);
	}
}
