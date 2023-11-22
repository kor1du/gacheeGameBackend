package com.gacheeGame.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gacheeGame.filter.JwtAuthenticationFilter;
import com.gacheeGame.filter.JwtExceptionFilter;
import com.gacheeGame.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;

    //Security 인증을 받지 않을 API 주소 목록
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                )
                .requestMatchers(
                        new AntPathRequestMatcher("/h2-console/**")
                )
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/member/oauth",
                        "/member/info",
                        "/category/categoryList",
                        "/question/questionList",
                        "/token/getToken"
                );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        //MemberAnswer
                        "/memberAnswer/memberAnswerList",
                        "/memberAnswer/saveAnswerList",
                        "/memberAnswer/matchedMemberList",

                        //Token
                        "/token/reissue"
                )
                .hasRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(objectMapper), JwtAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
