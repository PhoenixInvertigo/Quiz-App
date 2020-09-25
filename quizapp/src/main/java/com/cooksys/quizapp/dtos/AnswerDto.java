package com.cooksys.quizapp.dtos;

import org.springframework.stereotype.Component;

@Component
public class AnswerDto {

	private Long id;

	private String text;

	private Boolean correct;

	public AnswerDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}
}
