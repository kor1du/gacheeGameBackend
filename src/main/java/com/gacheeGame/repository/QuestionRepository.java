package com.gacheeGame.repository;

import com.gacheeGame.customRepository.CustomAnswerRepository;
import com.gacheeGame.customRepository.CustomQuestionRepository;
import com.gacheeGame.entity.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, CustomQuestionRepository
{
}
