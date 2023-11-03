package com.gacheeGame.repository;

import com.gacheeGame.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>
{
    //유저 ID로 검색
    Optional<Member> findByoAuthId(Long oAuthId);
}
