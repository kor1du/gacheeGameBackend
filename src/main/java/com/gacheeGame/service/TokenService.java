package com.gacheeGame.service;

import com.gacheeGame.dto.JwtTokenDto.Response;
import com.gacheeGame.entity.Member;
import com.gacheeGame.handler.CustomBadRequestException;
import com.gacheeGame.repository.MemberRepository;
import com.gacheeGame.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    //테스트용 토큰 발급
    public Response getToken(Long oAuthId) {
        try {
            //oAuthId로 멤버 검색
            Member member = memberRepository.findByoAuthId(oAuthId).get();

            //유저의 권한 파싱
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(member.getRole().toString()));

            //유저 정보로 AuthenticationToken 객체 생성
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getMemberId(), null, roles);

            //JWT 생성
            Response jwtTokenDto = jwtTokenProvider.generateToken(authenticationToken);

            return jwtTokenDto;
        } catch (Exception e) {
            log.error("테스트용 토큰 발급중 오류 발생", e);
            throw new CustomBadRequestException("테스트용 토큰을 발급하던중 오류가 발생했습니다.");
        }
    }

    //토큰 재발급
    public Response reissue(String authorization, String refreshToken) {
        try {
            String accessToken = jwtTokenProvider.parseAuthorizationHeader(authorization);

            Response jwtTokenDto = jwtTokenProvider.reissue(accessToken, refreshToken);

            return jwtTokenDto;
        } catch (Exception e) {
            log.error("토큰을 재발급하던중 오류가 발생했습니다.", e);
            throw new CustomBadRequestException("토큰을 재발급하던중 오류가 발생했습니다.");
        }
    }
}
