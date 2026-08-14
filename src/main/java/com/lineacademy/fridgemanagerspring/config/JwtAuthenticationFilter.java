package com.lineacademy.fridgemanagerspring.config;

import com.lineacademy.fridgemanagerspring.domain.user.User;
import com.lineacademy.fridgemanagerspring.repository.UserRepository;
import com.lineacademy.fridgemanagerspring.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 1. 헤더에서 토큰 추출
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);    // "Bearer "가 7글자, 8번째부터 시작 -> index 7

            // 2. 토큰이 정상적인지 확인
            if (jwtUtil.validateToken(token)) {
                Long userId = jwtUtil.getUserIdFromToken(token);

                // 3. 사용자 정보를 DB 조회
                User user = userRepository.findById(userId).orElse(null);       // 조회해서 있으면 저장, 없으면 null을 저장해달라

                if (user != null && user.getDeletedAt() == null) {
                    // 4. 정상적이면 그걸 컨트롤러에서 사용할 수 있도록 함

                    // Spring Security는 지금 들어온 사용자 권한 정보를 SimpleGrantedAuthority객체에 저장함
                    // 그러한 권한 이름 앞에 "ROLE_"를 붙이는 것이 규칙
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, Collections.singletonList(authority));
                    // request가 들어와서 Controller와 Service를 흘러 가는 박스의 규격이 java는 엄격하고
                    // 그 박스 내부에 사용자 정보를 담을 수 있는 칸의 타입이 UsernamePasswordAuthenticationToken 규격으로 정해져 있음
                    // UsernamePasswordAuthenticationToken이라는 타입은 3칸(매개변수)를 기록할 수 있고
                    // 첫번쨰 칸(매개변수)에는 사용자 ID, 두번째 칸 (매개변수)에는 그 외 정보 객체, 세번째 칸(매개변수)에는 ROLE이 담김

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);    // 다음 작업(체인)으로 넘겨줌

    }
}
