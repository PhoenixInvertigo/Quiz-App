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

import com.cooksys.quizapp.dtos.OutgoingQuestionDto;
import com.cooksys.quizapp.dtos.OutgoingQuizDto;
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

	// Returns all quizzes in the database
	@GetMapping
	public List<OutgoingQuizDto> GetQuizzes() {
		List<OutgoingQuizDto> result = quizService.getAllQuizzes();
		return result;
	}

	// Posts the sent quiz to the database, returning the new quiz
	@PostMapping
	public OutgoingQuizDto PostQuiz(@RequestBody QuizDto quiz) {
		OutgoingQuizDto result = quizService.postQuiz(quiz);
		return result;
	}

	// Deletes the specified quiz, returning a copy of the deleted quiz
	@DeleteMapping("/{id}")
	public OutgoingQuizDto DeleteQuiz(@PathVariable("id") Long id) {
		OutgoingQuizDto result = quizService.deleteQuiz(id);
		return result;
	}

	// Renames the specified quiz to the new name, returning the renamed quiz
	@PatchMapping("/{id}/rename/{newName}")
	public OutgoingQuizDto PatchQuizName(@PathVariable("id") Long id, @PathVariable("newName") String newName) {
		OutgoingQuizDto result = quizService.patchQuizName(id, newName);
		return result;
	}

	// Retrieves a random question from the specified quiz
	@GetMapping("/{id}/random")
	public OutgoingQuestionDto GetRandomQuestion(@PathVariable("id") Long id) {
		OutgoingQuestionDto result = quizService.getRandomQuestion(id);
		return result;
	}

	// Adds the sent question to the specified quiz, returning the modified quiz
	@PatchMapping("/{id}/add")
	public OutgoingQuizDto PatchAddQuestion(@RequestBody QuestionDto question, @PathVariable("id") Long id) {
		OutgoingQuizDto result = quizService.patchAddQuestion(question, id);
		return result;
	}

	// Deletes the specified question from the specified quiz, returning the deleted
	// question
	@DeleteMapping("{id}/delete/{questionId}")
	public OutgoingQuestionDto DeleteQuestion(@PathVariable("id") Long id,
			@PathVariable("questionId") Long questionId) {
		OutgoingQuestionDto result = quizService.deleteQuestionFromQuiz(id, questionId);
		return result;
	}

}
