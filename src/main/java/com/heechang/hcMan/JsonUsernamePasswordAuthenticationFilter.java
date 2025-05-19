package com.heechang.hcMan;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        if (!request.getMethod().equals("POST")) {
            log.warn("지원하지 않는 인증 메서드: {}", request.getMethod());
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        try {
            Map<String, String> authRequest = objectMapper.readValue(request.getInputStream(), Map.class);
            String username = authRequest.get("username");
            String password = authRequest.get("password");

            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }
            username = username.trim();

            log.debug("인증 시도: username={}", username);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            setDetails(request, token);
            return this.getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            log.error("인증 요청 본문 파싱 실패", e);
            throw new AuthenticationServiceException("Failed to parse authentication request body");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        log.info("인증 성공: {}", authResult.getName());
        // 추가 로직 (예: JWT 발급) 가능
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
