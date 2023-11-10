package com.gacheeGame.controller;

import com.gacheeGame.dto.MemberDto.Info;
import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.service.MemberService;
import com.gacheeGame.util.JsonUtil;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController
{
    private final MemberService memberService;
 
    //oauth 로그인 / 회원가입
    @GetMapping("/oauth")
    public ResponseEntity<ResponseDto> oauth (String code)
    {
        HashMap<String, Object> resultMap = memberService.oauth(code);

        ResponseDto responseDto = ResponseDto
                                            .builder()
                                            .status(HttpStatus.OK.value())
                                            .body(JsonUtil.ObjectToJsonObject(resultMap))
                                            .message("로그인이 완료되었습니다.")
                                            .build();

        return ResponseEntity.ok(responseDto);
    }
    
    //유저 정보 조회
    @GetMapping("/info")
    public ResponseEntity<ResponseDto> info (Long memberId)
    {
        Info memberInfoDto = memberService.info(memberId);

        ResponseDto responseDto = ResponseDto
                                            .builder()
                                            .status(HttpStatus.OK.value())
                                            .body(JsonUtil.ObjectToJsonObject("memberInfo", memberInfoDto))
                                            .message("유저 정보를 성공적으로 불러왔습니다.")
                                            .build();

        return ResponseEntity.ok(responseDto);
    }
}
