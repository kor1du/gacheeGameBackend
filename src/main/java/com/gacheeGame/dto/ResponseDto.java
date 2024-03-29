package com.gacheeGame.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto
{
    private int status; //응답 코드
    private Object body; //응답 데이터
    private String message; //응답 메시지
}