package com.cooksys.quizapp.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
	// Returns 404 if the specified quiz does not exist
	@DeleteMapping("/{id}")
	public ResponseEntity<OutgoingQuizDto> DeleteQuiz(@PathVariable("id") Long id) {
		ResponseEntity<OutgoingQuizDto> result = quizService.deleteQuiz(id);
		return result;
	}

	// Renames the specified quiz to the new name, returning the renamed quiz
	// Returns 404 if the specified quiz does not exist
	@PatchMapping("/{id}/rename/{newName}")
	public ResponseEntity<OutgoingQuizDto> PatchQuizName(@PathVariable("id") Long id,
			@PathVariable("newName") String newName) {
		ResponseEntity<OutgoingQuizDto> result = quizService.patchQuizName(id, newName);
		return result;
	}

	// Retrieves a random question from the specified quiz
	// Returns 404 if the specified quiz does not exist or has no questions
	@GetMapping("/{id}/random")
	public ResponseEntity<OutgoingQuestionDto> GetRandomQuestion(@PathVariable("id") Long id) {
		ResponseEntity<OutgoingQuestionDto> result = quizService.getRandomQuestion(id);
		return result;
	}

	// Adds the sent question to the specified quiz, returning the modified quiz
	// Returns 404 if the specified quiz does not exist
	@PatchMapping("/{id}/add")
	public ResponseEntity<OutgoingQuizDto> PatchAddQuestion(@RequestBody QuestionDto question,
			@PathVariable("id") Long id) {
		ResponseEntity<OutgoingQuizDto> result = quizService.patchAddQuestion(question, id);
		return result;
	}

	// Deletes the specified question from the specified quiz, returning the deleted
	// question
	// Returns 404 if the specified quiz or the specified question do not exist
	// or if the specified quiz does not contain the specified question
	@DeleteMapping("{id}/delete/{questionId}")
	public ResponseEntity<OutgoingQuestionDto> DeleteQuestion(@PathVariable("id") Long id,
			@PathVariable("questionId") Long questionId) {
		ResponseEntity<OutgoingQuestionDto> result = quizService.deleteQuestionFromQuiz(id, questionId);
		return result;
	}

}
