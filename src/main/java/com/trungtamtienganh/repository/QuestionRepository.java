package com.trungtamtienganh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trungtamtienganh.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	List<Question> findAllByTypeAndExamIdOrderByStt(int type, Integer examId);

	boolean existsBySttAndExamId(int stt, int examId);
}
