package com.gacheeGame.mapper;

import com.gacheeGame.dto.MemberAnswerDto;
import com.gacheeGame.dto.MemberDto;
import com.gacheeGame.entity.Member;
import com.gacheeGame.entity.MemberAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberAnswerMapper
{
}
