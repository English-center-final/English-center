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
@IdClass(WordNote_PK.class)
public class WordNote {

	@Id
	@ManyToOne
	@JoinColumn(name = "word_note_category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_wordnote_wordnotecategory"))
	private WordNoteCategory wordNoteCategory;

	@Id
	@ManyToOne
	@JoinColumn(name = "word_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_wordnote_word"))
	private Word word;
	
}