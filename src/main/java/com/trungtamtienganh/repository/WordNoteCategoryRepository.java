package com.trungtamtienganh.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trungtamtienganh.entity.WordNoteCategory;


public interface WordNoteCategoryRepository extends JpaRepository<WordNoteCategory, Integer> {

	List<WordNoteCategory> findAllByUserUsername(String username);

	boolean existsByUserUsernameAndId(String username, Integer id);
	
}