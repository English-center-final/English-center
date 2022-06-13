package com.trungtamtienganh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trungtamtienganh.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(String name);
}