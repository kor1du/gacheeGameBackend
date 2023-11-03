package com.gacheeGame.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gacheeGame.dto.MemberAnswerDto;
import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.entity.Answer;
import com.gacheeGame.entity.Member;
import com.gacheeGame.entity.MemberAnswer;
import com.gacheeGame.handler.CustomBadRequestException;
import com.gacheeGame.mapper.MemberAnswerMapper;
import com.gacheeGame.repository.AnswerRepository;
import com.gacheeGame.repository.MemberAnswerRepository;
import com.gacheeGame.repository.MemberRepository;
import com.gacheeGame.util.JsonUtil;
import com.gacheeGame.util.SessionUtil;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberAnswerService
{
    private final MemberAnswerRepository memberAnswerRepository;
    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;

    public ResponseDto memberAnswerList(Long matchedMemberId)
    {
        try{
            HashMap<String, Object> resultMap = new HashMap<>();
            HashMap<String, Object> memberAnswerListMap = new HashMap<>();

            Long memberId = Long.parseLong(SessionUtil.getUser().getUsername());

            if(matchedMemberId != null)
            {
                List<MemberAnswerDto.AnswerList> matchedMemberAnswerList = memberAnswerRepository
                    .findByMemberId(matchedMemberId)
                    .get()
                    .stream()
                    .map(a -> MemberAnswerDto.AnswerList
                        .builder()
                        .answerId(a.getAnswer().getAnswerId())
                        .questionId(a.getAnswer().getQuestion().getQuestionId())
                        .build())
                    .collect(Collectors.toList());

                memberAnswerListMap.put("matchedMemberAnswerList", matchedMemberAnswerList);
                resultMap.put("matchScore", getMatchScore(memberId,matchedMemberId));
                resultMap.put("matchedMemberName", memberRepository.findById(matchedMemberId).get().getName());
            }

            List<MemberAnswerDto.AnswerList> memberAnswerList = memberAnswerRepository
                                                                                    .findByMemberId(memberId)
                                                                                    .get()
                                                                                    .stream()
                                                                                    .map(a -> MemberAnswerDto.AnswerList
                                                                                    .builder()
                                                                                    .answerId(a.getAnswer().getAnswerId())
                                                                                    .questionId(a.getAnswer().getQuestion().getQuestionId())
                                                                                    .build())
                                                                                    .collect(Collectors.toList());

            memberAnswerListMap.put("myAnswerList", memberAnswerList);
            resultMap.put("memberAnswerList", memberAnswerListMap);

            return ResponseDto
                .builder()
                .status(HttpStatus.OK.value())
                .body(JsonUtil.ObjectToJsonObject(resultMap))
                .message("답변 리스트들을 정상적으로 불러왔습니다.")
                .build();
        }catch (Exception e) {
            log.error("내 답변 리스트들을 불러오던 중 오류가 발생했습니다.", e);
            throw new CustomBadRequestException("내 답변 리스트들을 불러오던 중 오류가 발생했습니다.");
        }
    }

    @Transactional
    public ResponseDto saveAnswerList(MemberAnswerDto.Request memberAnswerDtoRequest)
    {
        try{
            List<Answer> answerList = answerRepository.findByAnswerIdIn(memberAnswerDtoRequest.getAnswerList()).get();

            Long memberId = Long.parseLong(SessionUtil.getUser().getUsername());
            Long matchedMemberId = memberAnswerDtoRequest.getMatchedMemberId();

            Member member = memberRepository.findById(memberId).get();
            Member matchedMember = matchedMemberId != null ? memberRepository.findById(matchedMemberId).get() : null;

            List<MemberAnswer> memberAnswerList = answerList
                                                            .stream()
                                                            .map(a -> MemberAnswer
                                                                                .builder()
                                                                                .answer(a)
                                                                                .member(member)
                                                                                .matchedMember(matchedMember)
                                                                                .createdAt(new Date())
                                                                                .updatedAt(new Date())
                                                                                .build())
                                                            .collect(Collectors.toList());

            memberAnswerRepository.saveAll(memberAnswerList);

            return ResponseDto
                .builder()
                .status(HttpStatus.OK.value())
                .message("답변이 저장되었습니다.")
                .build();
        }catch (Exception e) {
            log.error("답변을 저장하던중 오류가 발생했습니다.", e);
            throw new CustomBadRequestException("답변을 저장하던중 오류가 발생했습니다.");
        }
    }

    public ResponseDto matchedMemberList(Long categoryId)
    {
        try{
            Long matchedMemberId = Long.parseLong(SessionUtil.getUser().getUsername());

            List<MemberAnswerDto.MatchedDto> matchedMemberList = memberAnswerRepository
                .findMatchedUser(categoryId, matchedMemberId)
                .get()
                .stream()
                .map(ma -> MemberAnswerDto.MatchedDto
                    .builder()
                    .memberId(ma.getMember().getMemberId())
                    .profileImage(ma.getMember().getProfileImage())
                    .name(ma.getMember().getName())
                    .email(ma.getMember().getEmail())
                    .isFirstTime(ma.getMember().isFirstTime())
                    .gender(ma.getMember().getGender())
                    .social(ma.getMember().getSocial())
                    .createdAt(ma.getCreatedAt())
                    .updatedAt(ma.getUpdatedAt())
                    .matchScore(getMatchScore(ma.getMember().getMemberId(), matchedMemberId))
                    .build())
                .distinct()
                .collect(Collectors.toList());

            return ResponseDto
                .builder()
                .status(HttpStatus.OK.value())
                .body(JsonUtil.ObjectToJsonObject("matchedMemberList", matchedMemberList))
                .message("유저 목록을 정상적으로 불러왔습니다.")
                .build();
        }catch (Exception e){
            log.error("유저 목록을 불러오던 중 오류가 발생했습니다.", e);
            throw new CustomBadRequestException("유저 목록을 불러오던 중 오류가 발생했습니다.");
        }
    }

    public double getMatchScore(Long memberId, Long matchedMemberId)
    {
        List<MemberAnswer> memberAnswerList = memberAnswerRepository.findByMemberId(memberId).get();
        List<MemberAnswer> matchedMemberAnswerList = memberAnswerRepository.findByMemberId(matchedMemberId).get();

        double sum = 0;

        for(int i = 0; i < memberAnswerList.size(); i++)
        {
            Long memberAnswerId = memberAnswerList.get(i).getAnswer().getAnswerId();
            Long matchedMemberAnswerId = matchedMemberAnswerList.get(i).getAnswer().getAnswerId();

            if(memberAnswerId == matchedMemberAnswerId)
                sum++;
        }

        return Math.round(sum / memberAnswerList.size() * 100);
    }
}
