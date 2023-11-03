package com.gacheeGame.mapper;

import com.gacheeGame.dto.MemberDto;
import com.gacheeGame.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper
{
    @Mapping(source = "OAuthId", target = "oAuthId")
    @Mapping(source = "firstTime", target = "isFirstTime")
    public MemberDto.Info memberToInfoDto(Member member);
}
