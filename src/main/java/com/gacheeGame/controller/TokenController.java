package com.gacheeGame.controller;

import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.service.TokenService;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(tokenService.getToken(oAuthId));
    }
}
