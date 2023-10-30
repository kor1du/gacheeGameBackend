package com.gacheeGame.filter;

import ch.qos.logback.core.spi.ErrorCodes;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gacheeGame.dto.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter
{
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request ,HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        try{
            filterChain.doFilter(request, response);
        }catch (SecurityException |
                IllegalArgumentException |
                JwtException e) {
            setErrorResponse(response, e.getMessage());
        }
    }

    private void setErrorResponse(HttpServletResponse response, String errorMessage) throws IOException
    {
        int responseStatus = HttpStatus.UNAUTHORIZED.value();

        ResponseDto responseDto = ResponseDto
            .builder()
            .httpStatus(responseStatus)
            .message(errorMessage)
            .build();

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(responseStatus);
        response.getWriter().print(objectMapper.valueToTree(responseDto));
    }
}
