package com.trungtamtienganh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trungtamtienganh.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

	boolean existsByIdNotAndName(Integer id, String name);
}
