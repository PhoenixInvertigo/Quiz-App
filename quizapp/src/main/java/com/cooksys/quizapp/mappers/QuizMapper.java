package com.cooksys.quizapp.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.quizapp.dtos.OutgoingQuizDto;
import com.cooksys.quizapp.dtos.QuizDto;
import com.cooksys.quizapp.entities.Quiz;

@Mapper(componentModel = "spring")
public interface QuizMapper {

	OutgoingQuizDto entityToDto(Quiz quiz);

	List<OutgoingQuizDto> entitiesToDtos(List<Quiz> quizzes);

	Quiz dtoToEntity(QuizDto quizDto);

	List<Quiz> dtosToEntities(List<QuizDto> quizDtos);

}
