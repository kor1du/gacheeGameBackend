package com.gacheeGame.service;

import com.gacheeGame.dto.JwtTokenDto;
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

    public ResponseDto getToken(Long oAuthId)
    {
        Member member = memberRepository.findByoAuthId(oAuthId).get();

        JwtTokenDto.Response response = getJwtTokenDto(member);

        return ResponseDto.builder()
            .status(HttpStatus.OK.value())
            .body(JsonUtil.ObjectToJsonObject("jwtToken", response))
            .message("테스트용 토큰이 생성되었습니다.")
            .build();
    }

    //JwtTokenDto 객체 반환
    private JwtTokenDto.Response getJwtTokenDto(Member member)
    {
        //Authentication 객체 생성
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getRole().toString()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getMemberId(), null, roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //JwtTokenDto 객체 생성
        JwtTokenDto.Response jwtTokenDto = jwtTokenProvider.generateToken(authentication);

        return jwtTokenDto;
    }
}
