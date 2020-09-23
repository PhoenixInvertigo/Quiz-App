package com.cooksys.quizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.quizapp.entities.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
