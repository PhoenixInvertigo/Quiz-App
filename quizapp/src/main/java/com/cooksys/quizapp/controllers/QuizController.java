package com.cooksys.quizapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.quizapp.dtos.QuestionDto;
import com.cooksys.quizapp.dtos.QuizDto;
import com.cooksys.quizapp.services.QuizService;

@RestController
@RequestMapping("/quiz")
public class QuizController {

	private QuizService quizService;

	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	@GetMapping
	public List<QuizDto> GetQuizzes() {
		List<QuizDto> result = quizService.getAllQuizzes();
		return result;
	}

	@PostMapping
	public QuizDto PostQuiz(@RequestBody QuizDto quiz) {
		QuizDto result = quizService.postQuiz(quiz);
		return result;
	}

	@DeleteMapping("/{id}")
	public QuizDto DeleteQuiz(@PathVariable("id") Long id) {
		QuizDto result = quizService.deleteQuiz(id);
		return result;
	}

	@PatchMapping("/{id}/rename/{newName}")
	public QuizDto PatchQuizName(@PathVariable("id") Long id, @PathVariable("newName") String newName) {
		QuizDto result = quizService.patchQuizName(id, newName);
		return result;
	}

	@GetMapping("/{id}/random")
	public QuestionDto GetRandomQuestion(@PathVariable("id") Long id) {
		QuestionDto result = quizService.getRandomQuestion(id);
		return result;
	}

	@PatchMapping("/{id}/add")
	public QuizDto PatchAddQuestion(@RequestBody QuestionDto question, @PathVariable("id") Long id) {
		QuizDto result = quizService.patchAddQuestion(question, id);
		return result;
	}

	@DeleteMapping("{id}/delete/{questionId}")
	public QuestionDto DeleteQuestion(@PathVariable("id") Long id, @PathVariable("questionId") Long questionId) {
		QuestionDto result = quizService.deleteQuestionFromQuiz(id, questionId);
		return result;
	}

}
