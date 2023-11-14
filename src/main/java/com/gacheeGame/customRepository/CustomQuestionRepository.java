package com.gacheeGame.customRepository;

import com.gacheeGame.dto.QuestionDto;
import com.gacheeGame.dto.QuestionDto.Response;
import java.util.List;

public interface CustomQuestionRepository
{
    //categoryId에 속해있는 질문 목록조회
    List<Response> findQuestionAndAnswerList(Long categoryId);
}
