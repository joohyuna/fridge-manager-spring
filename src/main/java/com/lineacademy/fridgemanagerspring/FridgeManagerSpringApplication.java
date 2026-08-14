package com.lineacademy.fridgemanagerspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing  // Hibernate에서, 수정일을 자동으로 관리하도록 하기 위한 필수 어노테이션
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
        // exclude 비포함 시킬건데, Spring Security가 제공하는 기본 사용자 목록 관리 긴능을 끔
// 우리는 DB로 사용자 리스트를 관리하고 하니까 기본 기능을 꺼버림
public class FridgeManagerSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(FridgeManagerSpringApplication.class, args);
    }

}
