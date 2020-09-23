package com.cooksys.quizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.quizapp.entities.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
