package com.trungtamtienganh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trungtamtienganh.entity.Level;


public interface LevelRepository extends JpaRepository<Level, Integer> {
	
	boolean existsByIdNotAndName(Integer id, String name);

	boolean existsByName(String name);

	Optional<Level> findByName(String name);

	List<Level> findAllByNameContaining(String name);
	
	Optional<Level> findBySlug(String slug);
}