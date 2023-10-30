package com.gacheeGame.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gacheeGame.filter.JwtAuthenticationFilter;
import com.gacheeGame.filter.JwtExceptionFilter;
import com.gacheeGame.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig
{
    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;

    //Security 인증을 받지 않을 API 주소 목록
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
            .ignoring()
            .requestMatchers(
                new AntPathRequestMatcher("/h2-console/**"),
                new AntPathRequestMatcher("/swagger-ui/**"),
                new AntPathRequestMatcher("/v3/api-docs/**"),
                new AntPathRequestMatcher("/login")
            );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.
                csrf(AbstractHttpConfigurer::disable)
            .sessionManagement((sessionManagement)->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/token/reissue")).hasRole("USER")
                .anyRequest().authenticated())
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtExceptionFilter(objectMapper), JwtAuthenticationFilter.class)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
