package com.cooksys.quizapp.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.quizapp.dtos.AnswerDto;
import com.cooksys.quizapp.dtos.OutgoingAnswerDto;
import com.cooksys.quizapp.entities.Answer;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

	OutgoingAnswerDto entityToDto(Answer answer);

	List<OutgoingAnswerDto> entitiesToDtos(List<Answer> answers);

	Answer dtoToEntity(AnswerDto answerDto);

	List<Answer> dtosToEntities(List<AnswerDto> answerDtos);

}
