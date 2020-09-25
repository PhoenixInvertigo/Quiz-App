package com.cooksys.quizapp.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

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

	public List<QuizDto> getAllQuizzes() {
		List<QuizDto> result = quizMapper.entitiesToDtos(quizRepository.findAll());
		return result;
	}

	public QuizDto postQuiz(QuizDto quizDto) {
		Quiz quiz = quizMapper.dtoToEntity(quizDto);
		quiz = quizRepository.saveAndFlush(quiz);
		List<Question> questions = quiz.getQuestions();
		List<Answer> answers;
		for (Question q : questions) {
			q.setQuiz(quiz);
			questionRepository.saveAndFlush(q);
			answers = q.getAnswers();
			for (Answer a : answers) {
				a.setQuestion(q);
				answerRepository.saveAndFlush(a);
			}
		}
		QuizDto result = quizMapper.entityToDto(quiz);
		return result;
	}

	public QuizDto deleteQuiz(Long id) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		Quiz quiz = null;
		if (optionalQuiz.isPresent()) {
			quiz = optionalQuiz.get();
		} else {
			return null;
		}
		QuizDto result = quizMapper.entityToDto(quiz);
		quizRepository.deleteById(quiz.getId());

		return result;
	}

	public QuizDto patchQuizName(Long id, String newName) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		Quiz quiz = null;
		if (optionalQuiz.isPresent()) {
			quiz = optionalQuiz.get();
		} else {
			return null;
		}
		quiz.setName(newName);
		quiz = quizRepository.saveAndFlush(quiz);
		QuizDto result = quizMapper.entityToDto(quiz);
		return result;
	}

	public QuestionDto getRandomQuestion(Long id) {
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
		QuestionDto result = questionMapper.entityToDto(randomQuestion);
		return result;
	}

	public QuizDto patchAddQuestion(QuestionDto question, Long id) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		Quiz quiz = null;
		if (optionalQuiz.isPresent()) {
			quiz = optionalQuiz.get();
		} else {
			return null;
		}
		Question questionEntity = questionMapper.dtoToEntity(question);
		questionEntity.setQuiz(quiz);
		questionEntity = questionRepository.saveAndFlush(questionEntity);
		for (Answer a : questionEntity.getAnswers()) {
			a.setQuestion(questionEntity);
			answerRepository.saveAndFlush(a);
		}
		List<Question> questions = quiz.getQuestions();
		questions.add(questionMapper.dtoToEntity(question));
		quiz.setQuestions(questions);
		quiz = quizRepository.saveAndFlush(quiz);
		QuizDto result = quizMapper.entityToDto(quiz);
		return result;
	}

	public QuestionDto deleteQuestionFromQuiz(Long id, Long questionId) {
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
		QuestionDto result = questionMapper.entityToDto(question);
		return result;
	}

}
