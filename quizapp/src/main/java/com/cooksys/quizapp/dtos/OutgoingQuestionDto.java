package com.cooksys.quizapp.dtos;

import java.util.List;

import org.springframework.stereotype.Component;

//This dto keeps the "correct" field of its answers hidden

@Component
public class OutgoingQuestionDto {

	private Long id;

	private String text;

	private List<OutgoingAnswerDto> answers;

	public OutgoingQuestionDto() {
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

	public List<OutgoingAnswerDto> getAnswers() {
		return answers;
	}

	public void setAnswers(List<OutgoingAnswerDto> answers) {
		this.answers = answers;
	}

}
