package com.gacheeGame.service;

import com.gacheeGame.dto.AnswerDto;
import com.gacheeGame.dto.AnswerDto.AnswerList;
import com.gacheeGame.dto.MemberAnswerDto;
import com.gacheeGame.dto.MemberAnswerDto.MatchedDto;
import com.gacheeGame.entity.MemberAnswer;
import com.gacheeGame.handler.CustomBadRequestException;
import com.gacheeGame.mapper.MemberAnswerMapper;
import com.gacheeGame.repository.AnswerRepository;
import com.gacheeGame.repository.CategoryRepository;
import com.gacheeGame.repository.MemberAnswerRepository;
import com.gacheeGame.repository.MemberRepository;
import com.gacheeGame.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberAnswerService {
    private final MemberAnswerRepository memberAnswerRepository;
    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;
    private final MemberAnswerMapper memberAnswerMapper;
    private final CategoryRepository categoryRepository;

    // 상대방 or 내 답변 가져오기
    @Transactional(readOnly = true)
    public HashMap<String, Object> memberAnswerList(Long matchedMemberId, Long categoryId) {
        try {
            HashMap<String, Object> resultMap = new HashMap<>();
            HashMap<String, Object> memberAnswerListMap = new HashMap<>();

            //내 유저 정보 조회
            Long memberId = Long.parseLong(SessionUtil.getUser().getUsername());

            //내 답변 목록 조회
            List<AnswerDto.AnswerList> myAnswerList = answerRepository.findAnswerList(memberId, matchedMemberId, categoryId);

            //내 답변 목록 Map에 저장
            memberAnswerListMap.put("myAnswerList", myAnswerList);

            //공유받은 링크에 답장시
            if (matchedMemberId != null) {
                //상대방 답변 조회
                List<AnswerDto.AnswerList> matchedMemberAnswerList = answerRepository.findAnswerList(matchedMemberId, null, categoryId);

                //결과 Map에 저장
                memberAnswerListMap.put("matchedMemberAnswerList", matchedMemberAnswerList);

                resultMap.put("matchScore", getMatchScore(myAnswerList, matchedMemberAnswerList));
                resultMap.put("matchedMemberName", memberRepository.findById(matchedMemberId).get().getName());
            }

            //답변 리스트 resultMap에 저장
            resultMap.put("memberAnswerList", memberAnswerListMap);

            return resultMap;
        } catch (Exception e) {
            log.error("내 답변 리스트들을 불러오던 중 오류가 발생했습니다.", e);
            throw new CustomBadRequestException("내 답변 리스트들을 불러오던 중 오류가 발생했습니다.");
        }
    }

    //답안 저장
    @Transactional
    public void saveAnswerList(MemberAnswerDto.Request memberAnswerDtoRequest) {
        try {
            //답안 파싱
            List<MemberAnswerDto.MemberAnswer> memberAnswerList = memberAnswerDtoRequest.getAnswerList();

            //내 유저 정보와 매칭된 유저 정보를 가져온다
            Long memberId = Long.parseLong(SessionUtil.getUser().getUsername());
            Long matchedMemberId = memberAnswerDtoRequest.getMatchedMemberId();

            //카테고리 ID 파싱
            Long categoryId = memberAnswerDtoRequest.getCategoryId();

            if (categoryRepository.findById(categoryId).isEmpty()) {
                throw new CustomBadRequestException("카테고리 ID가 올바르지 않습니다!");
            }

            //기존 답변목록 조회 및 삭제
            List<MemberAnswer> oldMemberAnswerList = memberAnswerRepository.findMemberAnswer(memberId, matchedMemberId, categoryId);

            if (!oldMemberAnswerList.isEmpty() || oldMemberAnswerList != null) {
                //삭제대상 IdList
                List<Long> deleteIdList = oldMemberAnswerList
                        .stream()
                        .map(o -> o.getMemberAnswerId())
                        .collect(Collectors.toList());

                //삭제
                memberAnswerRepository.deleteAllById(deleteIdList);
            }

            //새로운 MemberAnswer 리스트 생성
            List<MemberAnswer> memberAnswer = memberAnswerList
                    .stream()
                    .map(ma -> memberAnswerMapper.map(ma.getAnswerId(), categoryId, memberId, matchedMemberId, ma.getComment(), new Date(), new Date()))
                    .collect(Collectors.toList());

            //DB 저장
            memberAnswerRepository.saveAll(memberAnswer);
        } catch (CustomBadRequestException e) {
            throw e;
        } catch (Exception e) {
            log.error("답변을 저장하던중 오류가 발생했습니다.", e);
            throw new CustomBadRequestException("답변을 저장하던중 오류가 발생했습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<MatchedDto> matchedMemberList(Long categoryId) {
        try {
            Long myMemberId = Long.parseLong(SessionUtil.getUser().getUsername());

            //나와 매칭된 유저 목록 조회
            List<MatchedDto> matchedMemberList = memberAnswerRepository.findMatchedMemberList(categoryId, myMemberId);

            if (!matchedMemberList.isEmpty() && matchedMemberList != null) {
                //내 답변 조회
                List<AnswerList> myAnswerList = answerRepository.findAnswerList(myMemberId, null, categoryId);

                //matchScore 계산
                matchedMemberList
                        .stream()
                        .forEach(m -> m.setMatchScore(getMatchScore(myAnswerList, answerRepository.findAnswerList(m.getMemberId(), myMemberId, categoryId))));
            }

            return matchedMemberList;
        } catch (Exception e) {
            log.error("유저 목록을 불러오던 중 오류가 발생했습니다.", e);
            throw new CustomBadRequestException("유저 목록을 불러오던 중 오류가 발생했습니다.");
        }
    }

    public double getMatchScore(List<AnswerList> memberAnswerList, List<AnswerList> matchedMemberAnswerList) {
        double sum = 0;

        for (int i = 0; i < memberAnswerList.size(); i++) {
            Long memberAnswerId = memberAnswerList.get(i).getAnswerId();
            Long matchedMemberAnswerId = matchedMemberAnswerList.get(i).getAnswerId();

            if (memberAnswerId == matchedMemberAnswerId)
                sum++;
        }

        return Math.round(sum / memberAnswerList.size() * 100);
    }
}
