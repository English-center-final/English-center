package com.trungtamtienganh.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String slug;
	private String image;
	@Lob
	private String description;

	@ManyToOne
	@JoinColumn(name = "topic_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_course_topic"))
	private Topic topic;

	@OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
	private List<CourseWord> words = new ArrayList<>();

	public Course(Integer id) {
		super();
		this.id = id;
	}

	public Course(String name, String slug, String image, String description, Topic topic) {
		super();
		this.name = name;
		this.slug = slug;
		this.image = image;
		this.description = description;
		this.topic = topic;
	}

}