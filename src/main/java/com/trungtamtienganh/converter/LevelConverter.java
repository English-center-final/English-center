package com.trungtamtienganh.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trungtamtienganh.dto.LevelDTO;
import com.trungtamtienganh.entity.Level;
import com.trungtamtienganh.repository.LevelRepository;
import com.trungtamtienganh.utils.CommonFuc;

@Component
public class LevelConverter {
	
	@Autowired
	private LevelRepository levelRepository;

	public LevelDTO toLevelDTO(Level route) {
		LevelDTO levelDTO = new LevelDTO();

		levelDTO.setId(route.getId());
		levelDTO.setName(route.getName());
		levelDTO.setDescription(route.getDescription());
		levelDTO.setContent(route.getContent());
		levelDTO.setImage(route.getImage());
		levelDTO.setSlug(route.getSlug());

		return levelDTO;
	}

	public Level toLevel(LevelDTO levelDTO) {
		
		Integer id = levelDTO.getId();

		Level levelResult = levelRepository.findById(id).orElse(new Level(0));
		levelResult.setId(levelDTO.getId());

		String name = levelDTO.getName();
		levelResult.setName(name);
		levelResult.setDescription(levelDTO.getDescription());
		levelResult.setContent(levelDTO.getContent());
		levelResult.setSlug(CommonFuc.toSlug(name));

		return levelResult;
	}
}
