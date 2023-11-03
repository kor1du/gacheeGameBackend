package com.gacheeGame.dto;

import com.gacheeGame.entity.Answer;
import com.gacheeGame.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class QuestionDto
{
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response
    {
        private Long questionId; //PK
        private String situation; //상황 설명
        private String situationImage; //상황 이미지
        private String title; //상황 제목
        private String titleImage; //상황 제목 이미지
        private String subTitle; //상황 서브제목
        private List<AnswerDto.Response> answerList;
    }
}
