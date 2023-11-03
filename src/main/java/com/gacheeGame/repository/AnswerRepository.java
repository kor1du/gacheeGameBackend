package com.gacheeGame.repository;

import com.gacheeGame.entity.Answer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>
{
    Optional<List<Answer>> findByAnswerIdIn(List<Long> answerList);
}
