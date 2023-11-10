package com.gacheeGame.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class JwtTokenDto
{
    @Data
    @Builder
    public static class Response
    {
        private String accessToken; //엑세스 토큰
        private Long accessTokenExpiresIn; //액세스 토큰 만료 시간
        private String refreshToken; //리프레시 토큰
    }

    @Builder
    @Data
    public static class Reissue
    {
        @NotEmpty(message = "accessToken이 존재하지 않습니다.")
        private String accessToken;
        @NotEmpty(message = "refreshToken이 존재하지 않습니다.")
        private String refreshToken;
    }
}
