package com.gacheeGame.repository;

import com.gacheeGame.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>
{
    Member findByUserId(Long userId);
}
