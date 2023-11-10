package com.gacheeGame.repository;

import com.gacheeGame.customRepository.CustomMemberAnswerRepository;
import com.gacheeGame.dto.MemberAnswerDto;
import com.gacheeGame.entity.MemberAnswer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAnswerRepository extends JpaRepository<MemberAnswer, Long>, CustomMemberAnswerRepository
{
}
