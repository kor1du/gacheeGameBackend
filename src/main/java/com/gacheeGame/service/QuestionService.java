package com.gacheeGame.service;

import com.gacheeGame.dto.QuestionDto;
import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.handler.CustomBadRequestException;
import com.gacheeGame.repository.QuestionRepository;
import com.gacheeGame.util.JsonUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService
{
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public ResponseDto questionList()
    {
        try {
            List<QuestionDto.Response> questionList = questionRepository
                                                                        .findAllQuestionsWithAnswers()
                                                                        .stream()
                                                                        .map(q -> modelMapper.map(q, QuestionDto.Response.class))
                                                                        .collect(Collectors.toList());

            return ResponseDto
                            .builder()
                            .status(HttpStatus.OK.value())
                            .body(JsonUtil.ObjectToJsonObject("questionList", questionList))
                            .message("질문 데이터를 정상적으로 불러왔습니다.")
                            .build();
        }catch (Exception e){
            log.error("질문 데이터를 불러오던 중 오류가 발생하였습니다.");
            throw new CustomBadRequestException("질문 데이터를 불러오던 중 오류가 발생하였습니다.");
        }
    }
}
