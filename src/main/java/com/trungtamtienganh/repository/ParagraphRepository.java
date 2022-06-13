package com.trungtamtienganh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trungtamtienganh.entity.Paragraph;

public interface ParagraphRepository extends JpaRepository<Paragraph, Integer> {

	@Query(value = "select p.*\r\n" + "from paragraph as p\r\n"
			+ "inner join question_paragraph as q_p on  p.id = q_p.paragraph_id\r\n"
			+ "inner join question as q on  q_p.question_id = q.id\r\n"
			+ "inner join exam as ex on q.exam_id = ex.id\r\n" + "where ex.id = ?1 and q.type = ?2\r\n"
			+ "group by id order by q.type", nativeQuery = true)
	List<Paragraph> findAllByExamIdAndQuestionType(Integer examId, int questionType);

	@Query(value = "select distinct(p.id)\r\n" + "from paragraph as p\r\n"
			+ "inner join question_paragraph as q_p on  p.id = q_p.paragraph_id\r\n"
			+ "inner join question as q on  q_p.question_id = q.id\r\n"
			+ "inner join exam as ex on q.exam_id = ex.id\r\n" + "where ex.id = ?1", nativeQuery = true)
	List<Integer> getIds(Integer examId);
}