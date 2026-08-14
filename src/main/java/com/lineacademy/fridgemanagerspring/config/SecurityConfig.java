package com.lineacademy.fridgemanagerspring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity      // 웹 관련 Spring Security를 켜줘
@EnableMethodSecurity  // 앞으로 권한 제어를 메서드에 따라서 접근 제한을 할게
@RequiredArgsConstructor

// request가 도착할 때마다 이러한 절차를 진행해주세요
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean // 사용할 때마다 동일한 기능을 이용하게 굄
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Spring Boot를 통해 프로그램이 한세선(1회 통신의 활동)에 대해 어떻게 관리하는가
        http.csrf(AbstractHttpConfigurer::disable)
                // 그 한 세선 안에서 사용자에 대한 식별을 위해 통신의 대상을 저장하지 않겠다
                // 왜? 사용자 확인을 JWT를 통해 헤더에서 매번 까서 볼꺼니까
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        "/users/create",
                        "/users/login",
                        "/users/password-reset",
                        "/notice/**"
                ).permitAll()   //지금 여기에 등록된 주소를 토큰이 없어도 허용하겠다
                .anyRequest().authenticated()  // 이 외의 모든 요청들은 인증 필요
                )
                // 이러한 처리 맨 앞단에 여기에 추가한 필요를 붙임
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
