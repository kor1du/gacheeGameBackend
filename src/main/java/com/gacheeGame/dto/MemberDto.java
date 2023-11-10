package com.gacheeGame.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberDto
{
    //로그인 dto
    @Data
    @Builder
    public static class Info
    {
        private Long memberId; //PK
        @JsonProperty(value = "oAuthId")
        private Long oAuthId; //OAuth 회원번호
        private String name; //이름
        private String email; //이메일
        private String gender; //성별
        private String profileImage; //프로필 이미지 경로
        private String social; //소셜 로그인 종류 (ex) kakao, google, naver, ...etc)
        @JsonProperty(value = "isFirstTime")
        private boolean isFirstTime; //처음 가입 여부;
    }
}
