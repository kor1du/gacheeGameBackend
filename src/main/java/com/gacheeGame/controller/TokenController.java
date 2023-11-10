package com.gacheeGame.controller;

import com.gacheeGame.dto.JwtTokenDto.Response;
import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.service.TokenService;
import com.gacheeGame.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController
{
    private final TokenService tokenService;

    @GetMapping("/getToken")
    public ResponseEntity<ResponseDto> getToken(@RequestParam Long oAuthId)
    {
        Response jwtTokenDto = tokenService.getToken(oAuthId);

        ResponseDto responseDto = ResponseDto
                                            .builder()
                                            .status(HttpStatus.OK.value())
                                            .body(JsonUtil.ObjectToJsonObject("jwtToken", jwtTokenDto))
                                            .message("테스트용 토큰이 생성되었습니다.")
                                            .build();

        return ResponseEntity.ok(responseDto);
    }
}
