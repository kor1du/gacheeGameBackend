package com.gacheeGame.util;

import com.gacheeGame.dto.JwtTokenDto;
import com.gacheeGame.handler.CustomBadRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {
    private final String AUTHORITIES_KEY = "auth";
    private final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 1000L; //액세스 토큰 유효기간(1시간)
    //    private final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 1000L; //테스트용 액세스 토큰 유효기간(1분)
    private final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L; //refreshToken 유효기간(7일)
    //    private final long REFRESH_TOKEN_EXPIRE_TIME = 30 * 1000L; //테스트용 refreshToken 유효기간(10초)
    private RedisTemplate<String, Object> redisTemplate;
    private Key secretKey;

    public JwtTokenProvider(@Value("${jwt.secret.key}") String secretKey, RedisTemplate<String, Object> redisTemplate) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.redisTemplate = redisTemplate;
    }

    //Authorization 헤더에서 accessToken 파싱
    public String parseAuthorizationHeader(String authorization) {
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer")) {
            return authorization.substring(7);
        }

        return null;
    }

    //토큰 생성
    public JwtTokenDto.Response generateToken(Authentication authentication) {
        //권한 가져오기
        String authorities = getAuthorities(authentication);

        //accessToken 유효기간
        Long accessTokenExpiresIn = getExpiresIn(false);

        //refreshToken 유효기간
        Long refreshTokenExpiresIn = getExpiresIn(true);

        //accessToken 생성
        String accessToken = generateAccessToken(authentication.getName(), authorities, accessTokenExpiresIn);

        //refreshToken 생성
        String refreshToken = generateRefreshToken(authentication.getPrincipal(), refreshTokenExpiresIn);

        return JwtTokenDto
                .Response
                .builder()
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn)
                .refreshToken(refreshToken)
                .build();
    }

    //권한 가져오기
    private String getAuthorities(Authentication authentication) {
        String authorities = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return authorities;
    }

    //토큰 유효기간 계산
    private long getExpiresIn(Boolean isRefreshToken) {
        long now = (new Date()).getTime(); //현재 시간
        long expiresIn = now + (isRefreshToken ? REFRESH_TOKEN_EXPIRE_TIME : ACCESS_TOKEN_EXPIRE_TIME); //토큰 만료시간

        return expiresIn;
    }

    //accessToken 생성
    private String generateAccessToken(String name, String authorities, Long accessTokenExpiresIn) {
        String accessToken = Jwts.builder()
                .setSubject(name)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(accessTokenExpiresIn))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return accessToken;
    }

    //refreshToken 생성
    private String generateRefreshToken(Object principal, Long refreshTokenExpiresIn) {
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(refreshTokenExpiresIn))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        //redis에 refreshToken 저장
        redisTemplate.opsForValue()
                .set("RefreshToken UserId: " + principal,
                        refreshToken,
                        new Date(refreshTokenExpiresIn).getTime(),
                        TimeUnit.MILLISECONDS);

        return refreshToken;
    }

    //토큰의 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "유효기간이 지난 토큰입니다.");
        } catch (SecurityException e) {
            throw new SecurityException("토큰의 보안인증이 올바르지 않습니다.");
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("유효하지 않은 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("지원하지 않는 토큰형식 입니다.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("토큰값이 비어있습니다.");
        } catch (JwtException e) {
            throw new JwtException("토큰 인증시도중 알 수 없는 오류가 발생했습니다. 관리자에게 문의해주세요.");
        }
    }

    //accessToken 정보로 Authentication 객체 생성
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims
                        .get(AUTHORITIES_KEY)
                        .toString()
                        .split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    //토큰 정보 파싱
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    //토큰 재발행
    public JwtTokenDto.Response reissue(String accessToken, String refreshToken) {
        //사용자가 전송한 리프레쉬 토큰 유효성 검사
        validateToken(refreshToken);

        //토큰 정보로 Authentication 객체 생성
        Authentication authentication = getAuthentication(accessToken);

        //Authentication 객체에서 유저 ID 파싱
        String userId = ((User) authentication.getPrincipal()).getUsername();

        //redis에서 기존 refreshToken을 꺼내온다
        String oldRefreshToken = (String) redisTemplate
                .opsForValue()
                .get("RefreshToken UserId: " + userId);

        //redis에 저장되어 있는 기존 refreshToken과 토큰 재발급을 위해 사용자가 전송한 refreshToken이 일치하는지 검사
        if (!refreshToken.equals(oldRefreshToken)) {
            throw new CustomBadRequestException("Refresh Token 정보가 일치하지 않습니다!");
        }

        //권한 가져오기
        String authorities = getAuthorities(authentication);

        //accessToken 유효기간
        Long accessTokenExpiresIn = getExpiresIn(false);

        //accessToken 재발급
        String newAccessToken = generateAccessToken(userId, authorities, accessTokenExpiresIn);

        //재발급된 토큰 반환
        JwtTokenDto.Response responseDto = JwtTokenDto
                .Response
                .builder()
                .accessToken(newAccessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn)
                .refreshToken(refreshToken)
                .build();

        return responseDto;
    }
}
