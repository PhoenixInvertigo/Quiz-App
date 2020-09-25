package com.cooksys.quizapp.dtos;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class QuizDto {

	private Long id;

	private String name;

	private List<QuestionDto> questions;

	public QuizDto() {
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

	public List<QuestionDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDto> questions) {
		this.questions = questions;
	}

}
