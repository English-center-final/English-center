package com.trungtamtienganh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trungtamtienganh.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

	boolean existsByIdNotAndName(Integer id, String name);

	Optional<Branch> findByName(String name);

	List<Branch> findAllByNameContaining(String name);

}