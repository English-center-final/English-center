package com.trungtamtienganh.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import com.trungtamtienganh.entity.Classes;

@Transactional
@Component
public interface ClassesRepository extends JpaRepository<Classes, Integer>, JpaSpecificationExecutor<Classes> {

	Page<Classes> findAllByLevelSlugContaining(String levelSlug, Pageable pageable);

}