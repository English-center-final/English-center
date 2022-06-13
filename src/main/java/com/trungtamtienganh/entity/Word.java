package com.trungtamtienganh.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Word {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String mean;
	private String type;
	private String pronounce;
	private String sound;
	@Lob
	private String definition;
	@Lob
	private String example;
	private String image;

	public Word(Integer id) {
		super();
		this.id = id;
	}

}