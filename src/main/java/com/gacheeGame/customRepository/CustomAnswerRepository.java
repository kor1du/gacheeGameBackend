package com.gacheeGame.customRepository;

import com.gacheeGame.dto.AnswerDto;
import com.gacheeGame.dto.AnswerDto.AnswerList;
import java.util.List;

public interface CustomAnswerRepository
{
    //answerId로 해당 질문이 속해있는 카테고리 조회
    Long findCategoryId(Long answerId);

    //답안 목록 조회
    List<AnswerList> findAnswerList (Long memberId, Long matchedMemberId, Long category);
}
