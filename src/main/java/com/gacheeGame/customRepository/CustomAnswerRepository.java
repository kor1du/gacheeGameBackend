package com.gacheeGame.customRepository;

import com.gacheeGame.dto.AnswerDto;
import com.gacheeGame.dto.AnswerDto.AnswerList;
import java.util.List;

public interface CustomAnswerRepository
{
    //답안 목록 조회
    List<AnswerList> findAnswerList (Long memberId, Long matchedMemberId, Long category);
}
