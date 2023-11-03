package com.gacheeGame.repository;

import com.gacheeGame.entity.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>
{
    //Question과 Answer테이블 join 데이터 조회
    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.answerList")
    List<Question> findAllQuestionsWithAnswers();
}
