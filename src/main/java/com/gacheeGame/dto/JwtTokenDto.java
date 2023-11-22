package com.gacheeGame.dto;

import lombok.Builder;
import lombok.Data;

public class JwtTokenDto {

    //토큰 발급 dto
    @Data
    @Builder
    public static class Response {
        private String accessToken; //엑세스 토큰
        private Long accessTokenExpiresIn; //액세스 토큰 만료 시간
        private String refreshToken; //리프레시 토큰
    }
}
