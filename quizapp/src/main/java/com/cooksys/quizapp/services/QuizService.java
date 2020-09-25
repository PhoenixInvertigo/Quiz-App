package com.cooksys.quizapp.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cooksys.quizapp.dtos.OutgoingQuestionDto;
import com.cooksys.quizapp.dtos.OutgoingQuizDto;
import com.cooksys.quizapp.dtos.QuestionDto;
import com.cooksys.quizapp.dtos.QuizDto;
import com.cooksys.quizapp.entities.Answer;
import com.cooksys.quizapp.entities.Question;
import com.cooksys.quizapp.entities.Quiz;
import com.cooksys.quizapp.mappers.AnswerMapper;
import com.cooksys.quizapp.mappers.QuestionMapper;
import com.cooksys.quizapp.mappers.QuizMapper;
import com.cooksys.quizapp.repositories.AnswerRepository;
import com.cooksys.quizapp.repositories.QuestionRepository;
import com.cooksys.quizapp.repositories.QuizRepository;

@Service
public class QuizService {

	private QuizMapper quizMapper;

	private QuestionMapper questionMapper;

	private AnswerMapper answerMapper;

	private QuizRepository quizRepository;

	private QuestionRepository questionRepository;

	private AnswerRepository answerRepository;

	public QuizService(QuizMapper quizMapper, QuestionMapper questionMapper, AnswerMapper answerMapper,
			QuizRepository quizRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
		this.quizMapper = quizMapper;
		this.questionMapper = questionMapper;
		this.answerMapper = answerMapper;
		this.quizRepository = quizRepository;
		this.questionRepository = questionRepository;
		this.answerRepository = answerRepository;
	}

	// Returns the full list of quizzes
	public List<OutgoingQuizDto> getAllQuizzes() {
		List<OutgoingQuizDto> result = quizMapper.entitiesToDtos(quizRepository.findAll());
		return result;
	}

	// Connects all the entities of the passed-in quiz, then saves it
	// and returns the saved quiz
	public OutgoingQuizDto postQuiz(QuizDto quizDto) {
		Quiz quiz = quizMapper.dtoToEntity(quizDto);
		List<Question> questions = quiz.getQuestions();
		List<Answer> answers;
		for (Question q : questions) {
			q.setQuiz(quiz);
			answers = q.getAnswers();
			for (Answer a : answers) {
				a.setQuestion(q);
			}
		}
		quizRepository.saveAndFlush(quiz);

		OutgoingQuizDto result = quizMapper.entityToDto(quiz);
		return result;
	}

	// Deletes the specified quiz, then returns a copy of the deleted
	// quiz
	public ResponseEntity<OutgoingQuizDto> deleteQuiz(Long id) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		Quiz quiz = null;
		if (optionalQuiz.isPresent()) {
			quiz = optionalQuiz.get();
		} else {
			return new ResponseEntity<OutgoingQuizDto>(HttpStatus.NOT_FOUND);
		}
		OutgoingQuizDto result = quizMapper.entityToDto(quiz);
		quizRepository.deleteById(quiz.getId());

		return new ResponseEntity<OutgoingQuizDto>(result, HttpStatus.OK);
	}

	// Gets the specified quiz, updates its name, saves it to the database,
	// and returns the new quiz
	public ResponseEntity<OutgoingQuizDto> patchQuizName(Long id, String newName) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		Quiz quiz = null;
		if (optionalQuiz.isPresent()) {
			quiz = optionalQuiz.get();
		} else {
			return new ResponseEntity<OutgoingQuizDto>(HttpStatus.NOT_FOUND);
		}
		quiz.setName(newName);
		quiz = quizRepository.saveAndFlush(quiz);
		OutgoingQuizDto result = quizMapper.entityToDto(quiz);
		return new ResponseEntity<OutgoingQuizDto>(result, HttpStatus.OK);
	}

	// Gets the specified quiz, then uses the random function to pick
	// a random question from the list and returns it
	public ResponseEntity<OutgoingQuestionDto> getRandomQuestion(Long id) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		Quiz quiz = null;
		if (optionalQuiz.isPresent()) {
			quiz = optionalQuiz.get();
		} else {
			return new ResponseEntity<OutgoingQuestionDto>(HttpStatus.NOT_FOUND);
		}
		if (quiz.getQuestions().isEmpty()) {
			return new ResponseEntity<OutgoingQuestionDto>(HttpStatus.NOT_FOUND);
		}
		Random rand = new Random();
		Question randomQuestion = quiz.getQuestions().get(rand.nextInt(quiz.getQuestions().size()));
		OutgoingQuestionDto result = questionMapper.entityToDto(randomQuestion);
		return new ResponseEntity<OutgoingQuestionDto>(result, HttpStatus.OK);
	}

	// Retrieves the specified quiz, adds the question to its answer
	// list, then iterates over all questions and their answers to
	// connect the entities. Finally, saves the new question and
	// returns the modified quiz
	public ResponseEntity<OutgoingQuizDto> patchAddQuestion(QuestionDto question, Long id) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		Quiz quiz = null;
		if (optionalQuiz.isPresent()) {
			quiz = optionalQuiz.get();
		} else {
			return new ResponseEntity<OutgoingQuizDto>(HttpStatus.NOT_FOUND);
		}
		Question questionEntity = questionMapper.dtoToEntity(question);
		List<Question> questions = quiz.getQuestions();
		questions.add(questionEntity);
		quiz.setQuestions(questions);
		questions = quiz.getQuestions();

		List<Answer> answers;
		for (Question q : questions) {
			q.setQuiz(quiz);
			answers = q.getAnswers();
			for (Answer a : answers) {
				a.setQuestion(q);
			}
		}
		questionRepository.saveAndFlush(questionEntity);
		OutgoingQuizDto result = quizMapper.entityToDto(quiz);
		return new ResponseEntity<OutgoingQuizDto>(result, HttpStatus.OK);
	}

	// Gets the specified quiz and the specified question, checks whether
	// the quiz contains that question, if so, removes the question from
	// the quiz (unlinking the entities), then saves the modified quiz to
	// the database. Finally, returns the deleted question.
	public ResponseEntity<OutgoingQuestionDto> deleteQuestionFromQuiz(Long id, Long questionId) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		Quiz quiz = null;
		if (optionalQuiz.isPresent()) {
			quiz = optionalQuiz.get();
		} else {
			return new ResponseEntity<OutgoingQuestionDto>(HttpStatus.NOT_FOUND);
		}
		Optional<Question> optionalQuestion = questionRepository.findById(questionId);
		Question question = null;
		if (optionalQuestion.isPresent()) {
			question = optionalQuestion.get();
		} else {
			return new ResponseEntity<OutgoingQuestionDto>(HttpStatus.NOT_FOUND);
		}
		List<Question> questions = quiz.getQuestions();
		if (questions.contains(question)) {
			questions.remove(question);
			quiz.setQuestions(questions);
		} else {
			return new ResponseEntity<OutgoingQuestionDto>(HttpStatus.NOT_FOUND);
		}
		quizRepository.saveAndFlush(quiz);
		OutgoingQuestionDto result = questionMapper.entityToDto(question);
		return new ResponseEntity<OutgoingQuestionDto>(result, HttpStatus.OK);
	}

}
