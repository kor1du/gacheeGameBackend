package com.gacheeGame.controller;

import com.gacheeGame.dto.QuestionDto.Response;
import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.service.QuestionService;
import com.gacheeGame.util.JsonUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController
{
    private final QuestionService questionService;

    @GetMapping("/questionList")
    public ResponseEntity<ResponseDto> questionList(@RequestParam Long categoryId)
    {
        List<Response> questionList = questionService.questionList(categoryId);

        ResponseDto responseDto = ResponseDto
                                            .builder()
                                            .status(HttpStatus.OK.value())
                                            .body(JsonUtil.ObjectToJsonObject("questionList", questionList))
                                            .message("질문 데이터를 정상적으로 불러왔습니다.")
                                            .build();

        return ResponseEntity.ok(responseDto);
    }
}
