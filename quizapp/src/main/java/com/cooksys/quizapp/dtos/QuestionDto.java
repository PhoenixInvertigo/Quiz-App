package com.cooksys.quizapp.dtos;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class QuestionDto {

	private String text;

	private List<AnswerDto> answers;

	public QuestionDto() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<AnswerDto> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerDto> answers) {
		this.answers = answers;
	}

}
