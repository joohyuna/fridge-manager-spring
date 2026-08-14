package com.lineacademy.fridgemanagerspring.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component// 이클래스를 Spring Bott에 Bean으로 등록한다는 어노테이션
// Bean : Spring이 직접 생성하고 관리하는 자바 객체를 뜻함
// 원래 클래스라면, 개발자가 생성하고(new JwtUtil()) 관리(사용)해야 하는데
// Component 어노테이션을 통해 Bean으로 등록하면 자바 엔진이 생성하고 사용하므로
// 이객체는 1번만 생성됨
// @Bean 어노테이션이 있는데, 이건 Method에 붙는 어노테이션

// 생명주기(LifeCyle) 메모리의 살아있는 기간 - Java 에게 의존
public class JwtUtil {
    // 맴버변수
    private final SecretKey key;

    public JwtUtil(
            // 환경변수를 불러오는 법
            // beans
            @Value("${jwt.secret}") String secretString
    ) {
        // JwtUtil을 이용한 객체를  생성할 때 멤버변수 Key 값을 집어넣게 되는데,
        // HAAC.SHA 알고리즘을 총한 암호화로 searchKeyString의 값을 UTF-8방식을 가져와서
        this.key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    // 맴버세서드
    public String generateToken(Long userId) {
        return Jwts.builder()
                .claim("id", userId) // 암호화할 객체의 값을 넣는 메서드
                .issuedAt(new Date(System.currentTimeMillis()))   // 발급일자를 지금 현재 시간()
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(key) // 토큰에 입력받은 secretKey를 보안키로
                .compact();  // 계약
    }

    // validateToken을 통해 검증한후, 정상 토튼일 때에만 실행
    public Long getUserIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Long.class);
    }

    public boolean validateToken(String token) {
        try {
            // key를 가지소 token을 까봤던니 에러가 나지 않으면 return true
            // 에러나면 throw
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch(JwtException | IllegalArgumentException e) {
            return false;

        }
    }
}
