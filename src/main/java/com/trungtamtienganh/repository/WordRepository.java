package com.trungtamtienganh.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trungtamtienganh.entity.Word;


public interface WordRepository extends JpaRepository<Word, Integer> {

	boolean existsByName(String name);

	Optional<Word> findByName(String name);

	Page<Word> findAllByNameContaining(String name, Pageable pageable);
}