package com.trungtamtienganh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamtienganh.dto.LevelDTO;
import com.trungtamtienganh.service.LevelService;

@RestController
@RequestMapping("/levels")
@CrossOrigin
public class LevelController {

	@Autowired
	private LevelService levelService;

	@GetMapping("")
	public List<LevelDTO> getListLevel(@RequestParam(name = "name", required = false, defaultValue = "") String name) {
		return levelService.getList(name);
	}

	@GetMapping("/{slug}")
	public LevelDTO getCourseBySlug(@PathVariable("slug") String slug) {

		return levelService.getBySlug(slug);
	}
}
