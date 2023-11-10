package com.gacheeGame.customRepository;

import com.gacheeGame.dto.QuestionDto;
import com.gacheeGame.dto.QuestionDto.Response;
import java.util.List;

public interface CustomQuestionRepository
{
    List<Response> findQuestionAndAnswerList();
}
