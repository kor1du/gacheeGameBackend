package com.gacheeGame.service;

import com.gacheeGame.dto.JwtTokenDto;
import com.gacheeGame.dto.JwtTokenDto.Response;
import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.entity.Member;
import com.gacheeGame.repository.MemberRepository;
import com.gacheeGame.util.JsonUtil;
import com.gacheeGame.util.JwtTokenProvider;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService
{
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    //테스트용 토큰 발급
    public Response getToken(Long oAuthId)
    {
        Member member = memberRepository.findByoAuthId(oAuthId).get();

        Response jwtTokenDto = jwtTokenProvider.getJwtTokenDto(member);

        return jwtTokenDto;
    }
}
