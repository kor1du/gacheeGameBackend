package com.gacheeGame.customRepository;

import com.gacheeGame.dto.MemberAnswerDto;
import com.gacheeGame.dto.MemberAnswerDto.MatchedDto;
import com.gacheeGame.entity.MemberAnswer;
import java.util.List;

public interface CustomMemberAnswerRepository
{
    List<MemberAnswer> findMemberAnswer (Long memberId, Long matchedMemberId, Long categoryId);

    List<MatchedDto> findMatchedMemberList (Long categoryId, Long matchedMemberId);
}
