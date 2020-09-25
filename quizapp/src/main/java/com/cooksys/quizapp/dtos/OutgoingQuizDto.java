package com.cooksys.quizapp.dtos;

import java.util.List;

import org.springframework.stereotype.Component;

//This dto keeps the "correct" field of its questions' answers hidden

@Component
public class OutgoingQuizDto {

	private Long id;

	private String name;

	private List<OutgoingQuestionDto> questions;

	public OutgoingQuizDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OutgoingQuestionDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<OutgoingQuestionDto> questions) {
		this.questions = questions;
	}

}
