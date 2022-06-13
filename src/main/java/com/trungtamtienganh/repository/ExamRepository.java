package com.trungtamtienganh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trungtamtienganh.entity.Exam;
import com.trungtamtienganh.entity.NameSlugOnly;

public interface ExamRepository extends JpaRepository<Exam, Integer> {

	Optional<Exam> findBySlug(String slug);

	@Query(value = "SELECT ex.name, ex.slug FROM exam ex where ex.book_id = ?1", nativeQuery = true)
	List<NameSlugOnly> getAllNameSlugOnlysByBookId(Integer bookId);

	Page<Exam> findAllByNameContainingAndBookNameContaining(String name, String bookName, Pageable pageable);

	boolean existsByIdNotAndName(Integer id, String name);
}
