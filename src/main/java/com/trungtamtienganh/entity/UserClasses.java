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
@IdClass(UserClasses_PK.class)
public class UserClasses {

	@Id
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_userclasses_user"))
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "exam_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_userclasses_classes"))
	private Classes classes;

	private String status;

}