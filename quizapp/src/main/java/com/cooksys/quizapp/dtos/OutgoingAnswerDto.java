package com.cooksys.quizapp.dtos;

import org.springframework.stereotype.Component;

@Component
public class OutgoingAnswerDto {

	private Long id;

	private String text;

	public OutgoingAnswerDto() {
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

}
