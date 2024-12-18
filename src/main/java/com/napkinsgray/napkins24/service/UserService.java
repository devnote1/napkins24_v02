package com.napkinsgray.napkins24.service;

import com.napkinsgray.napkins24.entity.User;
import com.napkinsgray.napkins24.repository.UserRepository;
import com.napkinsgray.napkins24.utils.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String password, String email) {
        if(userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 졵하는 사용자 이름입니다.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .email(email)
                .build();

        userRepository.save(user);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        // 비밀번호 검증
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 로그인
     *
     * @param username 사용자 이름
     * @param password 사용자 비밀번호
     * @return JWT 토큰
     */
    public String loginUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        return jwtTokenProvider.generateToken(user.getId(), user.getUsername(), "ROLE_USER");
    }

}
