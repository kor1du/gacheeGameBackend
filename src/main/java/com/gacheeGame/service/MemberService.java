package com.gacheeGame.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.gacheeGame.dto.JwtTokenDto;
import com.gacheeGame.dto.KakaoOAuthDto;
import com.gacheeGame.dto.MemberDto.Info;
import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.entity.Member;
import com.gacheeGame.enums.Role;
import com.gacheeGame.handler.CustomBadRequestException;
import com.gacheeGame.mapper.MemberMapper;
import com.gacheeGame.repository.MemberRepository;
import com.gacheeGame.util.JsonUtil;
import com.gacheeGame.util.JwtTokenProvider;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService
{
    @Value("${oauth.kakao.rest-api-key}")
    private String kakaoRestAPIKey; //카카오 RestAPI

    @Value("${oauth.kakao.redirect-uri}")
    private String kakaoRedirectURI; //카카오 Redirect URI

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberMapper memberMapper;

    //oauth 로그인 로직
    public HashMap<String, Object> oauth(String code)
    {
        return kakaoLogin(code);
    }

    public Info info(Long memberId)
    {
        try {
            Member member = memberRepository
                                            .findById(memberId)
                                            .orElseThrow(() -> new CustomBadRequestException("찾는 유저가 존재하지 않습니다."));

            Info memberInfoDto = memberMapper.memberToInfoDto(member);

            return memberInfoDto;
        }catch (CustomBadRequestException e) {
            throw e;
        }catch (Exception e) {
            log.error("유저 정보를 가져오던중 오류가 발생하였습니다.", e);
            throw new CustomBadRequestException("유저 정보를 가져오던중 오류가 발생하였습니다.");
        }
    }

    //카카오 액세스 토큰 발급
    private String getKakaoToken(String code)
    {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoRestAPIKey);
        params.add("redirect_uri", kakaoRedirectURI);
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
                                    .profileImage(kakaoAccount.get("profile").get("profile_image_url").textValue())
                                    .build();
    }

    @Transactional
    //카카오 회원번호로 db에서 멤버 조회 후 있으면 로그인처리 없으면 회원가입 후 로그인처리
    private HashMap<String, Object> kakaoLogin(String code)
    {
        try {
            //카카오 토큰 발급
            String accessToken = getKakaoToken(code);
            
            //카카오 유저 정보 조회
            KakaoOAuthDto.UserInfo userInfo = getKakaoUserInfo(accessToken);

            //카카오 회원번호로 가입된 기존 회원이 없으면 로그인, 기존 회원이 존재하면 회원가입처리
            Member member = memberRepository
                                            .findByoAuthId(userInfo.getUserId())
                                            .orElseGet(() -> signup(userInfo));

            //회원 정보를 가진 Info 객체 생성
            Info memberInfoDto = memberMapper.memberToInfoDto(member);
            
            //토큰 발급
            JwtTokenDto.Response jwtTokenDto = jwtTokenProvider.getJwtTokenDto(member);

            //응답값을 담을 HashMap 객체 생성
            HashMap<String, Object> resultMap = new HashMap<>();

            //멤버 정보, 토큰 정보 map에 저장
            resultMap.put("memberInfo", memberInfoDto);
            resultMap.put("jwtToken", jwtTokenDto);

            return resultMap;
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
        //회원가입용 멤버 객체 생성
        Member member = Member
            .builder()
            .oAuthId(userInfo.getUserId())
            .name(userInfo.getNickname())
            .gender(userInfo.getGender())
            .profileImage(userInfo.getProfileImage())
            .email(userInfo.getEmail())
            .social("kakao")
            .isFirstTime(true)
            .role(Role.ROLE_USER)
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();

        //DB 저장
        memberRepository.save(member);

        return member;
    }
}
