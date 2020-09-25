package com.cooksys.quizapp.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.quizapp.dtos.OutgoingQuestionDto;
import com.cooksys.quizapp.dtos.QuestionDto;
import com.cooksys.quizapp.entities.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

	OutgoingQuestionDto entityToDto(Question question);

	List<OutgoingQuestionDto> entitiesToDtos(List<Question> questions);

	Question dtoToEntity(QuestionDto questionDto);

	List<Question> dtosToEntities(List<QuestionDto> questionDtos);

}
