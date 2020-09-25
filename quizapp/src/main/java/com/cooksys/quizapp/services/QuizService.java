package com.cooksys.quizapp.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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

	public List<OutgoingQuizDto> getAllQuizzes() {
		List<OutgoingQuizDto> result = quizMapper.entitiesToDtos(quizRepository.findAll());
		return result;
	}

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

	public OutgoingQuizDto deleteQuiz(Long id) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		Quiz quiz = null;
		if (optionalQuiz.isPresent()) {
			quiz = optionalQuiz.get();
		} else {
			return null;
		}
		OutgoingQuizDto result = quizMapper.entityToDto(quiz);
		quizRepository.deleteById(quiz.getId());

		return result;
	}

	public OutgoingQuizDto patchQuizName(Long id, String newName) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		Quiz quiz = null;
		if (optionalQuiz.isPresent()) {
			quiz = optionalQuiz.get();
		} else {
			return null;
		}
		quiz.setName(newName);
		quiz = quizRepository.saveAndFlush(quiz);
		OutgoingQuizDto result = quizMapper.entityToDto(quiz);
		return result;
	}

	public OutgoingQuestionDto getRandomQuestion(Long id) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		Quiz quiz = null;
		if (optionalQuiz.isPresent()) {
			quiz = optionalQuiz.get();
		} else {
			return null;
		}
		if (quiz.getQuestions().isEmpty()) {
			return null;
		}
		Random rand = new Random();
		Question randomQuestion = quiz.getQuestions().get(rand.nextInt(quiz.getQuestions().size()));
		OutgoingQuestionDto result = questionMapper.entityToDto(randomQuestion);
		return result;
	}

	public OutgoingQuizDto patchAddQuestion(QuestionDto question, Long id) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		Quiz quiz = null;
		if (optionalQuiz.isPresent()) {
			quiz = optionalQuiz.get();
		} else {
			return null;
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
		return result;
	}

	public OutgoingQuestionDto deleteQuestionFromQuiz(Long id, Long questionId) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		Quiz quiz = null;
		if (optionalQuiz.isPresent()) {
			quiz = optionalQuiz.get();
		} else {
			return null;
		}
		Optional<Question> optionalQuestion = questionRepository.findById(questionId);
		Question question = null;
		if (optionalQuestion.isPresent()) {
			question = optionalQuestion.get();
		} else {
			return null;
		}
		List<Question> questions = quiz.getQuestions();
		if (questions.contains(question)) {
			questions.remove(question);
			quiz.setQuestions(questions);
		} else {
			return null;
		}
		quizRepository.saveAndFlush(quiz);
		OutgoingQuestionDto result = questionMapper.entityToDto(question);
		return result;
	}

}
