package com.trungtamtienganh.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(CourseWord_PK.class)
public class CourseWord {

	@Id
	@ManyToOne
	@JoinColumn(name = "course_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_courseword_course"))
	private Course course;

	@Id
	@ManyToOne
	@JoinColumn(name = "word_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_courseword_word"))
	private Word word;
}