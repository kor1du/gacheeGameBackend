package com.gacheeGame.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gacheeGame.dto.JwtTokenDto;
import com.gacheeGame.dto.KakaoOAuthDto;
import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.entity.Member;
import com.gacheeGame.enums.Role;
import com.gacheeGame.handler.CustomBadRequestException;
import com.gacheeGame.repository.MemberRepository;
import com.gacheeGame.util.JsonUtil;
import com.gacheeGame.util.JwtTokenProvider;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService
{
    @Value("${oauth.kakao.rest-api-key}")
    private String kakaoRestAPIKey; //카카오 RestAPI 키 값

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    //oauth 로그인 로직
    public ResponseDto oauth(String code)
    {
        return kakaoLogin(code);
    }

    //카카오 액세스 토큰 발급
    private String getKakaoToken(String code)
    {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoRestAPIKey);
        params.add("redirect_uri", "http://localhost:8085/login");
        params.add("code", code);

        WebClient webClient = WebClient
                                .builder()
                                .baseUrl("https://kauth.kakao.com")
                                .build();

        KakaoOAuthDto.Token kakaoOAuthToken = webClient
                                                        .post()
                                                        .uri("/oauth/token")
                                                        .bodyValue(params)
                                                        .retrieve()
                                                        .bodyToMono(KakaoOAuthDto.Token.class)
                                                        .block();

        return kakaoOAuthToken.getAccess_token();
    }

    //카카오 유저 정보 가져오기
    private KakaoOAuthDto.UserInfo getKakaoUserInfo(String accessToken)
    {
        WebClient webClient = WebClient
            .builder()
            .baseUrl("https://kapi.kakao.com")
            .build();

        String userInfo = webClient
                                    .get()
                                    .uri("/v2/user/me")
                                    .header("Authorization", "Bearer "+ accessToken)
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .block();

        JsonNode kakaoUserInfoJson = JsonUtil.jsonStringToJsonObject(userInfo);

        JsonNode kakaoAccount = kakaoUserInfoJson.get("kakao_account");

        return KakaoOAuthDto.UserInfo
                                    .builder()
                                    .userId(Long.parseLong(kakaoUserInfoJson.get("id").toPrettyString()))
                                    .nickname(kakaoAccount.get("profile").get("nickname").textValue())
                                    .email(kakaoAccount.get("email").textValue())
                                    .gender(kakaoAccount.get("gender").textValue())
                                    .build();
    }

    //카카오 회원번호로 db에서 멤버 조회 후 있으면 로그인처리 없으면 회원가입 후 로그인처리
    private ResponseDto kakaoLogin(String code)
    {
        try {
            String accessToken = getKakaoToken(code);
            KakaoOAuthDto.UserInfo userInfo = getKakaoUserInfo(accessToken);

            Member member = memberRepository.findByUserId(userInfo.getUserId());

            //카카오 회원번호로 가입된 기존 회원이 없음 (회원가입처리)
            if(member == null)
            {
                member = signup(userInfo);
            }

            JwtTokenDto.Response jwtTokenDto = getJwtTokenDto(member);

            HashMap<String,Object> resultMap = new HashMap<>();

            resultMap.put("user", member);
            resultMap.put("jwtToken", jwtTokenDto);

            return ResponseDto
                            .builder()
                            .httpStatus(HttpStatus.OK.value())
                            .body(JsonUtil.ObjectToJsonObject(resultMap))
                            .message("로그인이 완료되었습니다.")
                            .build();
        }catch (CustomBadRequestException e) {
            throw e;
        }catch (Exception e) {
            log.error("로그인 기능 수행중 오류 발생", e);
            throw new CustomBadRequestException("로그인중 오류가 발생하였습니다.");
        }
    }

    //회원가입
    private Member signup(KakaoOAuthDto.UserInfo userInfo)
    {
        Member member = Member
            .builder()
            .userId(userInfo.getUserId())
            .name(userInfo.getNickname())
            .gender(userInfo.getGender())
            .profileImage("")
            .social("kakao")
            .isFirst(true)
            .role(Role.USER)
            .build();

        memberRepository.save(member);

        return member;
    }

    //JwtTokenDto 객체 반환
    private JwtTokenDto.Response getJwtTokenDto(Member member)
    {
        //Authentication 객체 생성
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getRole().toString()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getUserId(), null, roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //JwtTokenDto 객체 생성
        JwtTokenDto.Response jwtTokenDto = jwtTokenProvider.generateToken(authentication);

        return jwtTokenDto;
    }
}
