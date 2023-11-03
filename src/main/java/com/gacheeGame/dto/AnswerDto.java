package com.gacheeGame.dto;

import com.gacheeGame.dto.QuestionDto.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AnswerDto
{
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response
    {
        private Long answerId; //PK
        private Long questionId; //FK (question)
        private String answerContent; //질문 내용
    }
}
