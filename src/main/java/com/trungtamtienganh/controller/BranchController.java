package com.trungtamtienganh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamtienganh.entity.Branch;
import com.trungtamtienganh.service.BranchService;

@RestController
@RequestMapping("/branches")
@CrossOrigin
public class BranchController {

	@Autowired
	private BranchService branchService;

	@GetMapping("")
	public List<Branch> getListBranch(@RequestParam(name = "name", required = false, defaultValue = "") String name) {
		return branchService.getList(name);
	}

}
