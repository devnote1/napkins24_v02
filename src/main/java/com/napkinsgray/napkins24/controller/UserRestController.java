package com.napkinsgray.napkins24.controller;

import com.napkinsgray.napkins24.dto.request.LoginDTO;
import com.napkinsgray.napkins24.dto.request.SignupDTO;
import com.napkinsgray.napkins24.dto.response.TokenResponse;
import com.napkinsgray.napkins24.service.UserService;
import com.napkinsgray.napkins24.utils.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/auth")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 API
     *
     * @param request 회원가입 요청 데이터 (JSON)
     * @return 공통 DTO(ApiUtil) 형식의 성공 메시지
     */
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ApiUtil<String>> registerUser(@RequestBody SignupDTO request) {
        userService.registerUser(request.getUsername(), request.getPassword(), request.getEmail());
        return ResponseEntity.ok(new ApiUtil<>("회원가입이 완료되었습니다."));
    }

    /**
     * 로그인 API
     *
     * @param request 로그인 요청 데이터 (JSON)
     * @return 공통 DTO(ApiUtil) 형식의 JWT 토큰 응답
     */
    @Operation(summary = "로그인", description = "사용자가 로그인하고 JWT 토큰을 발급받습니다.")
    @PostMapping("/signin")
    public ResponseEntity<ApiUtil<TokenResponse>> loginUser(@RequestBody LoginDTO request) {
        // JWT 토큰 생성
        String token = userService.loginUser(request.getUsername(), request.getPassword());

        // Bearer 접두어 추가
        TokenResponse response = new TokenResponse("Bearer " + token);
        return ResponseEntity.ok(new ApiUtil<>(response));
    }
}
