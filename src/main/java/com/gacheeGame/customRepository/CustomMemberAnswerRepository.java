package com.gacheeGame.customRepository;

import com.gacheeGame.dto.MemberAnswerDto;
import com.gacheeGame.dto.MemberAnswerDto.MatchedDto;
import com.gacheeGame.entity.MemberAnswer;
import java.util.List;

public interface CustomMemberAnswerRepository
{
    //유저 답변 목록 조회
    List<MemberAnswer> findMemberAnswer (Long memberId, Long matchedMemberId, Long categoryId);

    //나와 매칭된 유저 조회
    List<MatchedDto> findMatchedMemberList (Long categoryId, Long matchedMemberId);
}
