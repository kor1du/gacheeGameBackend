package com.gacheeGame.customRepository;

import com.gacheeGame.dto.AnswerDto;
import com.gacheeGame.dto.AnswerDto.AnswerList;
import com.gacheeGame.entity.QAnswer;
import com.gacheeGame.entity.QMemberAnswer;
import com.gacheeGame.entity.QQuestion;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAnswerRepositoryImpl implements CustomAnswerRepository
{
    private final JPAQueryFactory queryFactory;

    //Answer이 속해있는 카테고리 ID 조회
    @Override
    public Long findCategoryId(Long answerId)
    {
        QAnswer qAnswer = new QAnswer("a");
        QQuestion qQuestion = new QQuestion("q");

        return queryFactory
            .select(qQuestion.categoryId)
            .from(qAnswer)
            .leftJoin(qQuestion)
            .on(qQuestion.questionId.eq(qAnswer.answerId))
            .where(qAnswer.answerId.eq(answerId))
            .fetchFirst();
    }

    //답변 목록 조회
    @Override
    public List<AnswerList> findAnswerList (Long memberId, Long matchedMemberId, Long categoryId)
    {
        QAnswer qAnswer = new QAnswer("a");
        QQuestion qQuestion = new QQuestion("q");
        QMemberAnswer qMemberAnswer = new QMemberAnswer("ma");

        return queryFactory
            .select(Projections.fields(
                AnswerList.class,
                qAnswer.answerId,
                qQuestion.questionId))
            .from(qAnswer)
            .leftJoin(qQuestion)
            .on(qQuestion.questionId.eq(qAnswer.questionId))
            .where(qAnswer.answerId.in(JPAExpressions
                .select(qMemberAnswer.answerId)
                .from(qMemberAnswer)
                .where(
                    qMemberAnswer.memberId.eq(memberId),
                    qMemberAnswer.categoryId.eq(categoryId),
                    matchedMemberId == null ? qMemberAnswer.matchedMemberId.isNull() : qMemberAnswer.matchedMemberId.eq(matchedMemberId))
            ))
            .fetch();
    }
}
