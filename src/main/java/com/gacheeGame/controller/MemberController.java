package com.gacheeGame.controller;

import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController
{
    private final MemberService memberService;

    @GetMapping("/login")
    public ResponseEntity<ResponseDto> oauth(String code)
    {
        return ResponseEntity.ok(memberService.oauth(code));
    }
}
