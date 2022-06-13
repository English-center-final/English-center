package com.trungtamtienganh.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserExam_PK implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer user;
	private Integer exam;
}