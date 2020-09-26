package com.cooksys.quizapp.dtos;

import org.springframework.stereotype.Component;

@Component
public class AnswerDto {

	private String text;

	private Boolean correct;

	public AnswerDto() {
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
