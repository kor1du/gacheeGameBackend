package com.gacheeGame.service;

import com.gacheeGame.dto.QuestionDto;
import com.gacheeGame.dto.QuestionDto.Response;
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

    public List<Response> questionList()
    {
        try {
            List<Response> questionList = questionRepository.findQuestionAndAnswerList();

            return questionList;
        }catch (Exception e){
            log.error("질문 데이터를 불러오던 중 오류가 발생하였습니다.", e);
            throw new CustomBadRequestException("질문 데이터를 불러오던 중 오류가 발생하였습니다.");
        }
    }
}
