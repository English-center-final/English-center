package com.trungtamtienganh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamtienganh.converter.LevelConverter;
import com.trungtamtienganh.dto.LevelDTO;
import com.trungtamtienganh.entity.Level;
import com.trungtamtienganh.exception.MyExceptionHelper;
import com.trungtamtienganh.repository.LevelRepository;
import com.trungtamtienganh.service.AwsS3Service;
import com.trungtamtienganh.service.LevelService;
import com.trungtamtienganh.utils.MyConstant;

@Service
@Transactional
public class LevelServiceImpl implements LevelService {

	@Autowired
	private LevelRepository levelRepository;

	@Autowired
	private LevelConverter levelConverter;

	@Autowired
	private AwsS3Service awsS3Service;

	@Override
	public List<LevelDTO> getList(String name) {
		if (name == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		List<Level> levelPage = levelRepository.findAllByNameContaining(name);

		List<LevelDTO> levelDTOs = levelPage.stream().map(c -> levelConverter.toLevelDTO(c))
				.collect(Collectors.toList());
		
		for (LevelDTO levelDTO : levelDTOs) {
			levelDTO.setContent("");
		}

		return levelDTOs;
	}

	@Override
	public LevelDTO save(LevelDTO levelDTO) {
		validate(levelDTO);
		Level levelWasSave = levelRepository.save(levelConverter.toLevel(levelDTO));

		return levelConverter.toLevelDTO(levelWasSave);
	}

	private void validate(LevelDTO levelDTO) {
		if (levelDTO == null || levelDTO.getId() < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Integer id = levelDTO.getId();

		if (id != 0 && !levelRepository.existsById(id))
			throw MyExceptionHelper.throwResourceNotFoundException(MyConstant.LEVEL);

		if (levelRepository.existsByIdNotAndName(id, levelDTO.getName())) {
			Map<String, String> error = new HashMap<>();
			error.put("name", "Tên Level bị trùng");
			throw MyExceptionHelper.throwRuntimeCustomException(error);
		}
	}

	@Override
	public LevelDTO getBySlug(String slug) {
		if (slug == null)
			throw MyExceptionHelper.throwIllegalArgumentException();
		Level LEVEL = levelRepository.findBySlug(slug)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.LEVEL));

		return levelConverter.toLevelDTO(LEVEL);
	}

	@Override
	public String uploadImage(Integer id, MultipartFile image) {
		if (id == null || image == null)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Level level = levelRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.LEVEL));

		String imageString = level.getImage();
		if (imageString != null) {

			awsS3Service.deleteObjectFromUrl(imageString);
		}

		String imgUrl = awsS3Service.uploadObject(image);
		level.setImage(imgUrl);

		levelRepository.save(level);

		return imgUrl;
	}

	@Override
	public void delete(Integer id) {
		if (id == null || id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Level level = levelRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(MyConstant.LEVEL));

		String image = level.getImage();

		levelRepository.deleteById(id);

		if (image != null) {
			awsS3Service.deleteObjectFromUrl(image);
		}

	}

}
