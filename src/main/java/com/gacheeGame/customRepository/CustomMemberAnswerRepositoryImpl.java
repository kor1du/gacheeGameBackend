package com.gacheeGame.customRepository;

import com.gacheeGame.dto.MemberAnswerDto.MatchedDto;
import com.gacheeGame.entity.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomMemberAnswerRepositoryImpl implements CustomMemberAnswerRepository {
    private final JPAQueryFactory queryFactory;

    //기존 답변 목록 조회
    @Override
    public List<MemberAnswer> findMemberAnswer(Long memberId, Long matchedMemberId, Long categoryId) {
        QMemberAnswer qMemberAnswer = new QMemberAnswer("ma");
        QAnswer qAnswer = new QAnswer("a");
        QQuestion qQuestion = new QQuestion("q");

        return queryFactory
                .selectFrom(qMemberAnswer)
                .leftJoin(qAnswer)
                .on(qAnswer.answerId.eq(qMemberAnswer.answerId))
                .leftJoin(qQuestion)
                .on(qQuestion.questionId.eq(qAnswer.questionId))
                .where(
                        qMemberAnswer.memberId.eq(memberId),
                        matchedMemberId == null ? qMemberAnswer.matchedMemberId.isNull() : qMemberAnswer.matchedMemberId.eq(matchedMemberId),
                        qQuestion.categoryId.eq(categoryId))
                .fetch();
    }

    @Override
    public List<MatchedDto> findMatchedMemberList(Long categoryId, Long matchedMemberId) {
        QMemberAnswer qMemberAnswer = new QMemberAnswer("ma");
        QMember qMember = new QMember("m");
        QAnswer qAnswer = new QAnswer("a");
        QQuestion qQuestion = new QQuestion("q");

        return queryFactory
                .select(Projections.fields(
                        MatchedDto.class,
                        qMember.memberId,
                        qMember.profileImage,
                        qMember.name,
                        qMember.email,
                        qMember.isFirstTime,
                        qMember.gender,
                        qMember.social,
                        qMember.createdAt,
                        qMember.updatedAt
                ))
                .from(qMemberAnswer)
                .leftJoin(qMember)
                .on(qMember.memberId.eq(qMemberAnswer.memberId))
                .leftJoin(qAnswer)
                .on(qAnswer.answerId.eq(qMemberAnswer.answerId))
                .leftJoin(qQuestion)
                .on(qQuestion.questionId.eq(qAnswer.questionId))
                .where(
                        qMemberAnswer.matchedMemberId.eq(matchedMemberId),
                        qQuestion.categoryId.eq(categoryId))
                .groupBy(qMemberAnswer.memberId)
                .fetch();
    }
}
