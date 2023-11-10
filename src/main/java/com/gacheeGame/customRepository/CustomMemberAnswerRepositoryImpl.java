package com.gacheeGame.customRepository;

import com.gacheeGame.dto.MemberAnswerDto;
import com.gacheeGame.dto.MemberAnswerDto.MatchedDto;
import com.gacheeGame.entity.Answer;
import com.gacheeGame.entity.MemberAnswer;
import com.gacheeGame.entity.QAnswer;
import com.gacheeGame.entity.QMember;
import com.gacheeGame.entity.QMemberAnswer;
import com.gacheeGame.entity.QQuestion;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
public class CustomMemberAnswerRepositoryImpl implements CustomMemberAnswerRepository
{
    private final JPAQueryFactory queryFactory;

    //기존 답변 목록 조회
    @Override
    public List<MemberAnswer> findMemberAnswer (Long memberId, Long matchedMemberId, Long categoryId)
    {
        QMemberAnswer qMemberAnswer = new QMemberAnswer("ma");

        return queryFactory
            .selectFrom(qMemberAnswer)
            .where(
                qMemberAnswer.memberId.eq(memberId),
                matchedMemberId == null ? qMemberAnswer.matchedMemberId.isNull() : qMemberAnswer.matchedMemberId.eq(matchedMemberId),
                qMemberAnswer.categoryId.eq(categoryId))
            .fetch();
    }

    @Override
    public List<MatchedDto> findMatchedMemberList (Long categoryId, Long matchedMemberId)
    {
        QMemberAnswer qMemberAnswer = new QMemberAnswer("ma");
        QMember qMember = new QMember("m");

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
            .where(
                qMemberAnswer.matchedMemberId.eq(matchedMemberId),
                qMemberAnswer.categoryId.eq(categoryId))
            .groupBy(qMemberAnswer.memberId)
            .fetch();
    }
}
