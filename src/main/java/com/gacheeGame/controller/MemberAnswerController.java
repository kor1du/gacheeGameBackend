package com.gacheeGame.controller;

import com.gacheeGame.dto.MemberAnswerDto;
import com.gacheeGame.dto.MemberAnswerDto.MatchedDto;
import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.service.MemberAnswerService;
import com.gacheeGame.util.JsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class MemberAnswerController {
    private final MemberAnswerService memberAnswerService;

    // 상대방 혹은 내 답변 가져오기
    @GetMapping("/memberAnswerList")
    public ResponseEntity<ResponseDto> memberAnswerList(@RequestParam(required = false) Long matchedMemberId, @RequestParam Long categoryId) {
        HashMap<String, Object> resultMap = memberAnswerService.memberAnswerList(matchedMemberId, categoryId);

        ResponseDto responseDto = ResponseDto
                .builder()
                .status(HttpStatus.OK.value())
                .body(JsonUtil.ObjectToJsonObject(resultMap))
                .message("답변 리스트들을 정상적으로 불러왔습니다.")
                .build();

        return ResponseEntity.ok(responseDto);
    }

    //답안 저장
    @PostMapping("/saveAnswerList")
    public ResponseEntity<ResponseDto> saveAnswerList(@RequestBody MemberAnswerDto.Request memberAnswerDtoRequest) {
        memberAnswerService.saveAnswerList(memberAnswerDtoRequest);

        ResponseDto responseDto = ResponseDto
                .builder()
                .status(HttpStatus.OK.value())
                .message("답변이 저장되었습니다.")
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/matchedMemberList")
    public ResponseEntity<ResponseDto> matchedMemberList(@RequestParam Long categoryId) {
        List<MatchedDto> matchedMemberList = memberAnswerService.matchedMemberList(categoryId);

        ResponseDto responseDto = ResponseDto
                .builder()
                .status(HttpStatus.OK.value())
                .body(JsonUtil.ObjectToJsonObject("matchedMemberList", matchedMemberList))
                .message("유저 목록을 정상적으로 불러왔습니다.")
                .build();

        return ResponseEntity.ok(responseDto);
    }
}
