package com.trungtamtienganh.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import com.trungtamtienganh.utils.AudioAuditListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AudioAuditListener.class)
public class Audio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Lob
	private String name;

	@OneToOne
	@JoinColumn(name = "question_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_audio_question"))
	private Question question;

	public Audio(String name, Question question) {
		this.name = name;
		this.question = question;
	}

}