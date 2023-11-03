package com.gacheeGame.repository;

import com.gacheeGame.dto.MemberAnswerDto;
import com.gacheeGame.entity.MemberAnswer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAnswerRepository extends JpaRepository<MemberAnswer, Long>
{
    @Query("SELECT m "
        + "FROM MemberAnswer m "
        + "INNER JOIN FETCH m.answer "
        + "INNER JOIN FETCH m.answer.question "
        + "WHERE m.member.memberId = :memberId")
    Optional<List<MemberAnswer>> findByMemberId(Long memberId);

//    @Query("SELECT DISTINCT m.memberId as memberId, "
//        + "m.profileImage as profileImage, "
//        + "m.name as name, "
//        + "m.email as email, "
//        + "m.isFirstTime as isFirst, "
//        + "m.gender as gender, "
//        + "m.social as social, "
//        + "ma.createdAt as createdAt, "
//        + "ma.updatedAt as updatedAt "
//        + "FROM MemberAnswer ma "
//        + "INNER JOIN ma.member m "
//        + "INNER JOIN ma.answer a "
//        + "INNER JOIN ma.answer.question q "
//        + "WHERE q.category.categoryId = :categoryId AND ma.matchedMember.memberId = :matchedMemberId"
//    )
//@Query("SELECT DISTINCT ma.member.id, ma.member.profileImage, ma.member.name, ma.member.email, ma.member.isFirstTime, ma.member.gender, ma.member.social, ma.createdAt, ma.updatedAt "
//    + "FROM MemberAnswer ma "
//    + "INNER JOIN ma.member m ON ma.member.memberId = m.memberId "
//    + "INNER JOIN ma.answer a "
//    + "INNER JOIN ma.answer.question q "
//    + "WHERE q.category.categoryId = :categoryId AND ma.matchedMember.memberId = :matchedMemberId"
//)
@Query("SELECT ma "
    + "FROM MemberAnswer ma "
    + "INNER JOIN ma.member m ON ma.member.memberId = m.memberId "
    + "INNER JOIN ma.answer a "
    + "INNER JOIN ma.answer.question q "
    + "WHERE q.category.categoryId = :categoryId AND ma.matchedMember.memberId = :matchedMemberId"
)
    Optional<List<MemberAnswer>> findMatchedUser(Long categoryId, Long matchedMemberId);
}
