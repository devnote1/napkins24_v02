package com.napkinsgray.napkins24.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT 토큰 생성 및 검증 유틸 클래스
 * - 헤더(Header), 페이로드(Payload), 서명(Signature)으로 구성된 JWT를 처리합니다.
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret; // Base64로 인코딩된 Secret Key

    @Value("${jwt.expiration}")
    private Long jwtExpiration; // 토큰 만료 시간 (밀리초 단위)

    /**
     * JWT 토큰 생성
     * - 헤더: 기본 제공 (HMAC256 알고리즘 사용)
     * - 페이로드: 사용자 ID, 이름, 권한 정보 및 발급/만료 시간 포함
     * - 서명: Secret Key로 서명하여 변조 방지
     *
     * @param userId 사용자 ID
     * @param username 사용자 이름
     * @param roles 사용자 권한 정보
     * @return 생성된 JWT 토큰 문자열
     */
    public String generateToken(Long userId, String username, String roles) {
        return JWT.create()
                // 헤더(Header): 토큰의 타입과 알고리즘 정보를 포함
                .withSubject(username) // 토큰의 주제 (사용자명)

                // 페이로드(Payload): 데이터 클레임 추가
                .withClaim("userId", userId) // 사용자 ID
                .withClaim("username", username) // 사용자 이름 (추가된 부분)
                .withClaim("roles", roles) // 권한 정보
                .withIssuedAt(new Date()) // 발급 시간
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration)) // 만료 시간

                // 서명(Signature): Secret Key를 사용해 토큰 무결성 보장
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    /**
     * JWT 토큰 검증
     * - 서명(Signature)을 검증하여 변조 여부를 확인
     *
     * @param token 클라이언트에서 받은 JWT 토큰
     * @return 검증된 DecodedJWT 객체
     */
    private DecodedJWT validateToken(String token) {
        return JWT.require(Algorithm.HMAC256(jwtSecret))
                .build()
                .verify(token);
    }

    /**
     * 토큰에서 사용자 이름 추출
     *
     * @param token JWT 토큰
     * @return 사용자 이름 (username)
     */
    public String getUsernameFromToken(String token) {
        return validateToken(token).getClaim("username").asString(); // 페이로드의 username 클레임 반환
    }

    /**
     * 토큰에서 사용자 ID 추출
     *
     * @param token JWT 토큰
     * @return 사용자 ID
     */
    public Long getUserIdFromToken(String token) {
        return validateToken(token).getClaim("userId").asLong(); // 페이로드의 userId 클레임 반환
    }

    /**
     * 토큰에서 권한 정보(roles) 추출
     *
     * @param token JWT 토큰
     * @return 사용자 권한 (roles)
     */
    public String getRolesFromToken(String token) {
        return validateToken(token).getClaim("roles").asString(); // 페이로드의 roles 클레임 반환
    }
}
