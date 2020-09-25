package com.cooksys.quizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.quizapp.entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	public void deleteById(Long id);

}
