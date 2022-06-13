package com.trungtamtienganh.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.trungtamtienganh.utils.ParagraphAuditListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(ParagraphAuditListener.class)
public class Paragraph {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Lob
	private String content;
	private String image;
	private String transcript;

	@OneToMany(mappedBy = "paragraph", cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
	private List<QuestionParagraph> questionParagraphs;

	public Paragraph(Integer id) {
		super();
		this.id = id;
	}

	public int getNumberPart() {

		return questionParagraphs.get(0).getQuestion().getType();
	}
}