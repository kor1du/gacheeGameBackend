package com.gacheeGame.controller;

import com.gacheeGame.dto.MemberAnswerDto;
import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.service.MemberAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memberAnswer")
@RequiredArgsConstructor
public class MemberAnswerController
{
    private final MemberAnswerService memberAnswerService;

    @GetMapping("/memberAnswerList")
    public ResponseEntity<ResponseDto> memberAnswerList(@RequestParam(required = false) Long matchedMemberId)
    {
        return ResponseEntity.ok(memberAnswerService.memberAnswerList(matchedMemberId));
    }

    @PostMapping("/saveAnswerList")
    public ResponseEntity<ResponseDto> saveAnswerList(@RequestBody MemberAnswerDto.Request memberAnswerDtoRequest)
    {
        return ResponseEntity.ok(memberAnswerService.saveAnswerList(memberAnswerDtoRequest));
    }

    @GetMapping("/matchedMemberList")
    public ResponseEntity<ResponseDto> matchedMemberList(@RequestParam Long categoryId)
    {
        return ResponseEntity.ok(memberAnswerService.matchedMemberList(categoryId));
    }
}
