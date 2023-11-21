package com.gacheeGame.customRepository;

import com.gacheeGame.dto.AnswerDto.AnswerList;
import com.gacheeGame.entity.QAnswer;
import com.gacheeGame.entity.QMemberAnswer;
import com.gacheeGame.entity.QQuestion;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomAnswerRepositoryImpl implements CustomAnswerRepository {
    private final JPAQueryFactory queryFactory;

    //답변 목록 조회
    @Override
    public List<AnswerList> findAnswerList(Long memberId, Long matchedMemberId, Long categoryId) {
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
                .where(
                        qQuestion.categoryId.eq(categoryId),
                        qAnswer.answerId.in(JPAExpressions
                                .select(qMemberAnswer.answerId)
                                .from(qMemberAnswer)
                                .where(
                                        qMemberAnswer.memberId.eq(memberId),
                                        matchedMemberId == null ? qMemberAnswer.matchedMemberId.isNull() : qMemberAnswer.matchedMemberId.eq(matchedMemberId))
                        ))
                .fetch();
    }
}
