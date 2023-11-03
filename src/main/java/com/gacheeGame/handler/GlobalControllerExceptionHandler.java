package com.gacheeGame.handler;

import com.gacheeGame.dto.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler
{
    //기능 실행중 미리 지정해둔 오류 발생시 아래 오류 handler에서 처리
    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<ResponseDto> handleBadRequestException(CustomBadRequestException e)
    {
        return setErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    //컨트롤러에서 토큰 인증 처리중 오류 발생시 아래 오류 handler에서 처리
    @ExceptionHandler({ ExpiredJwtException.class,
                        SecurityException.class,
                        MalformedJwtException.class,
                        UnsupportedJwtException.class,
                        IllegalArgumentException.class,
                        JwtException.class})
    public ResponseEntity<ResponseDto> handleJwtException(Exception e)
    {
        return setErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    //컨트롤러 호출시 필수 인자가 누락되어있다면 아래 오류 handler에서 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        return setErrorResponse(HttpStatus.BAD_REQUEST, e.getFieldError().getDefaultMessage());
    }

    //errorResponse 생성
    private ResponseEntity<ResponseDto> setErrorResponse(HttpStatus httpStatus, String errorMessage)
    {
        ResponseDto responseDto = ResponseDto.builder()
            .status(httpStatus.value())
            .message(errorMessage)
            .build();

        return ResponseEntity.status(httpStatus).body(responseDto);
    }
}