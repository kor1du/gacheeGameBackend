package com.gacheeGame.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

public class MemberAnswerDto {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchedDto {
        private Long memberId;
        private String profileImage;
        private String name;
        private String email;
        @JsonProperty(value = "isFirstTime")
        private boolean isFirstTime;
        private String gender;
        private String social;
        private Date createdAt;
        private Date updatedAt;
        private double matchScore;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private List<MemberAnswer> answerList; // 유저가 선택한 답변의 PK 리스트
        private Long matchedMemberId; //링크 공유한 유저 ID
        private Long categoryId; //카테고리 ID
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberAnswer {
        private Long answerId;
        private String comment;
    }
}
