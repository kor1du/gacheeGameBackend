package com.gacheeGame.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class KakaoOAuthDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Token {
        private String token_type; //토큰 종류(bearer로 고정)
        private String access_token; //사용자 액세스 토큰 값
        private Integer expires_in; //액세스 토큰과 ID토큰의 만료 시간(초)
        private String refresh_token; //사용자 리프레시 토큰 값
        private Integer refresh_token_expires_in; //리프레시 토큰 만료 시간(초)
        private String scope; //인증된 사용자의 정보 조회 권한 범위
    }

    @Data
    @Builder
    public static class UserInfo {
        private Long userId; //카카오 회원번호
        private String nickname; //사용자 별명(이름)
        private String email; //사용자 이메일
        private String gender; //사용자 성별
        private String profileImage; //사용자 프로필 이미지 경로
    }
}
