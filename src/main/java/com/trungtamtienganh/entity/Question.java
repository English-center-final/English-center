package com.trungtamtienganh.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int stt;
	private int type;
	private String content;
	private String a;
	private String b;
	private String c;
	private String d;
	private String result;
	private String extra;

	@OneToOne(mappedBy = "question", cascade = CascadeType.REMOVE)
	private Audio audio;

	@ManyToOne
	@JoinColumn(name = "exam_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_question_exam"))
	private Exam exam;

	@OneToOne(mappedBy = "question")
	private QuestionParagraph questionParagraph;

	public Question(Integer id) {
		super();
		this.id = id;
	}

}