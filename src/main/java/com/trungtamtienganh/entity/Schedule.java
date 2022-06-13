package com.trungtamtienganh.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private LocalDate date;
	private String description;
	private String room;
	private String session;
	private boolean status;

	@ManyToOne
	@JoinColumn(name = "classes_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_schedule_classes"))
	private Classes classes;

	public Schedule(Integer id) {
		super();
		this.id = id;
	}
}
