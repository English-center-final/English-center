package com.trungtamtienganh.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trungtamtienganh.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Page<User> findAllByUsernameContaining(String username, Pageable pageable);

	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

	Optional<User> findById(Integer id);
}