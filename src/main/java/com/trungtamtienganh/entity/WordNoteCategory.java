package com.trungtamtienganh.entity;

import java.time.LocalDate;
import java.util.ArrayList;
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
public class WordNoteCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private LocalDate createDate;

	@OneToMany(mappedBy = "wordNoteCategory", cascade = CascadeType.REMOVE)
	private List<WordNote> words = new ArrayList<WordNote>();

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_wordnotecategory_user"))
	private User user;

	public WordNoteCategory(Integer id) {
		super();
		this.id = id;
	}
	
	public WordNoteCategory(String name, LocalDate createDate, User user) {
		super();
		this.name = name;
		this.createDate = createDate;
		this.user = user;
		
	}

}