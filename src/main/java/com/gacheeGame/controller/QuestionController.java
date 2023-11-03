package com.gacheeGame.controller;

import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController
{
    private final QuestionService questionService;

    @GetMapping("/questionList")
    public ResponseEntity<ResponseDto> questionList()
    {
        return ResponseEntity.ok(questionService.questionList());
    }
}
