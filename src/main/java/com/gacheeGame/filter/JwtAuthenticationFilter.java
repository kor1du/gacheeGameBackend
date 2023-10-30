package com.gacheeGame.filter;

import com.gacheeGame.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean
{
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
    {
        String jwtToken = resolveToken((HttpServletRequest) request); //클라이언트가 전송한 토큰 정보

        if(jwtToken != null && jwtTokenProvider.validateToken(jwtToken)) //토큰정보가 존재 && 토큰이 유효함
        {
            Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);
    }

    private String resolveToken(HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer"))
        {
            return bearerToken.substring(7);
        }

        return null;
    }
}
