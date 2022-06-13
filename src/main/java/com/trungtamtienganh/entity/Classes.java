package com.trungtamtienganh.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Classes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer amount;
	private LocalDate dateStart;
	private Integer numOfLessons;
	
	@Column(length = 32, columnDefinition = "varchar(32) default 'ONGOING'")
	@Enumerated(EnumType.STRING)
	private ClassStatus status;
//	private String description;
	private String date;
	
	@OneToMany(mappedBy = "classes", cascade = CascadeType.ALL)
	private List<Schedule> schedules = new ArrayList<Schedule>();

	@ManyToOne
	@JoinColumn(name = "level_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_classes_level"))
	private Level level;
	
	@ManyToOne
	@JoinColumn(name = "branch_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_classes_branch"))
	private Branch branch;

	public Classes(Integer id) {
		super();
		this.id = id;
	}

}
