package com.cooksys.quizapp.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.quizapp.dtos.QuizDto;
import com.cooksys.quizapp.entities.Quiz;

@Mapper(componentModel = "spring")
public interface QuizMapper {

	QuizDto entityToDto(Quiz quiz);

	List<QuizDto> entitiesToDtos(List<Quiz> quizzes);

	Quiz dtoToEntity(QuizDto quizDto);

	List<Quiz> dtosToEntities(List<QuizDto> quizDtos);

}
