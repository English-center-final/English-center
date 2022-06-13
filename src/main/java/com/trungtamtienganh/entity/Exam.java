package com.trungtamtienganh.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String slug;
//	private String part1Audio;
//	private String part2Audio;
//	private String part3Audio;
//	private String part4Audio;

	@OneToMany(mappedBy = "exam", cascade = CascadeType.REMOVE)
	private List<Question> questions;

	@ManyToOne
	@JoinColumn(name = "book_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_exam_book"))
	private Book book;

	public Exam(Integer id) {

		this.id = id;
	}

}