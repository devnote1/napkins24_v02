package com.napkinsgray.napkins24.controller;

import com.napkinsgray.napkins24.dto.LoginRequest;
import com.napkinsgray.napkins24.dto.SignupRequest;
import com.napkinsgray.napkins24.dto.TokenResponse;
import com.napkinsgray.napkins24.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 API
     *
     * @param request 회원가입 요청 데이터 (JSON)
     * @return 회원가입 완료 메시지
     */
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignupRequest request) {
        userService.registerUser(request.getUsername(), request.getPassword(), request.getEmail());
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    /**
     * 로그인 API
     *
     * @param request 로그인 요청 데이터 (JSON)
     * @return Bearer 토큰 (JSON 형식)
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(@RequestBody LoginRequest request) {
        // JWT 토큰 생성
        String token = userService.loginUser(request.getUsername(), request.getPassword());

        // Bearer 접두어 추가 (공백을 추가해 주자!)
        TokenResponse response = new TokenResponse("Bearer " + token);
        return ResponseEntity.ok(response);
    }
}
