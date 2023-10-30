package com.gacheeGame.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseDto
{
    private int httpStatus; //응답 코드
    private Object body; //응답 데이터
    private String message; //응답 메시지
}