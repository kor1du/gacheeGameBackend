package com.gacheeGame.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AnswerDto
{
    //질문 답안 목록
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response
    {
        private Long answerId; //PK
        private Long questionId; //FK (question)
        private String answerContent; //질문 내용
    }

    //선택한 답안 목록
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerList
    {
        private Long answerId;
        private Long questionId;
    }
}
