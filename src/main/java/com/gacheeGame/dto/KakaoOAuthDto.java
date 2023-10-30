package com.gacheeGame.dto;

import lombok.Builder;
import lombok.Data;

public class KakaoOAuthDto
{
    @Data
    public static class Token
    {
        String token_type; //토큰 종류(bearer로 고정)
        String access_token; //사용자 액세스 토큰 값
        Integer expires_in; //액세스 토큰과 ID토큰의 만료 시간(초)
        String refresh_token; //사용자 리프레시 토큰 값
        Integer refresh_token_expires_in; //리프레시 토큰 만료 시간(초)
        String scope; //인증된 사용자의 정보 조회 권한 범위
    }

    @Data
    @Builder
    public static class UserInfo
    {
        Long userId; //카카오 회원번호
        String nickname ; //사용자 별명(이름)
        String email; //사용자 이메일
        String gender; //사용자 성별
    }
}
