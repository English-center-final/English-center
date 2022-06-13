package com.trungtamtienganh.entity;

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
public class QuestionParagraph {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "question_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_questionparagraph_question"))
	private Question question;

	@ManyToOne
	@JoinColumn(name = "paragraph_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_coursesubscribe_paragraph"))
	private Paragraph paragraph;

	public QuestionParagraph(Question question, Paragraph paragraph) {
		super();
		this.question = question;
		this.paragraph = paragraph;
	}

}