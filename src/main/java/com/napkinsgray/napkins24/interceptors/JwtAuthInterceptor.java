package com.napkinsgray.napkins24.interceptors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.napkinsgray.napkins24.exceptions.errors.Exception401;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Authorization 헤더에서 JWT 토큰 추출
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new Exception401("Authorization 헤더가 없거나 올바르지 않습니다.");
        }

        String token = authHeader.substring(7); // "Bearer " 이후 토큰만 추출
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtSecret))
                    .build()
                    .verify(token);

            // 사용자 ID 추출
            Long userId = decodedJWT.getClaim("userId").asLong();
            request.setAttribute("userId", userId);
        } catch (Exception e) {
            throw new Exception401("유효하지 않은 JWT 토큰입니다.");
        }

        // true --> Controller 진입
        return true;
    }
}
