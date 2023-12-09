package com.gacheeGame.mapper;

import com.gacheeGame.dto.MemberAnswerDto;
import com.gacheeGame.entity.Answer;
import com.gacheeGame.entity.Member;
import com.gacheeGame.entity.MemberAnswer;

import java.util.Date;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberAnswerMapper {
    @Mappings({
            @Mapping(source = "createdAt", target = "createdAt"),
            @Mapping(source = "updatedAt", target = "updatedAt")
    })
    MemberAnswer map(Long answerId, Long categoryId, Long memberId, Long matchedMemberId, String comment, Date createdAt, Date updatedAt);
}
