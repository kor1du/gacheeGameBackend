package com.gacheeGame.customRepository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.gacheeGame.dto.AnswerDto.Response;
import com.gacheeGame.dto.QuestionDto;
import com.gacheeGame.entity.QAnswer;
import com.gacheeGame.entity.QQuestion;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository
{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<QuestionDto.Response> findQuestionAndAnswerList(Long categoryId)
    {
        QQuestion qQuestion = new QQuestion("q");
        QAnswer qAnswer = new QAnswer("a");

        return queryFactory
            .from(qQuestion)
            .leftJoin(qAnswer)
            .on(qAnswer.questionId.eq(qQuestion.questionId))
            .orderBy(qAnswer.answerId.asc())
            .where(qQuestion.categoryId.eq(categoryId))
            .transform(
                        groupBy(qQuestion.questionId)
                        .list(
                                Projections.fields(
                                                    QuestionDto.Response.class,
                                                    qQuestion.questionId,
                                                    qQuestion.situation,
                                                    qQuestion.situationImage,
                                                    qQuestion.title,
                                                    qQuestion.titleImage,
                                                    qQuestion.subTitle,
                                                    list(
                                                        Projections.fields(
                                                                            Response.class,
                                                                            qAnswer.answerId,
                                                                            qAnswer.questionId,
                                                                            qAnswer.answerContent
                                                                            )
                                                    ).as("answerList")
                                )
                        )
            );
    }
}
