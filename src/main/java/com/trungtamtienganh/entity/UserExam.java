package com.trungtamtienganh.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
//@IdClass(UserExam_PK.class)
public class UserExam {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

//	@Id
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_userexam_user"))
	private User user;

//	@Id
	@ManyToOne
	@JoinColumn(name = "exam_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_userexam_exam"))
	private Exam exam;
	
	private String partResult;
	private int listenPoint;
	private int readPoint;
	private LocalDateTime testDate;
	
}