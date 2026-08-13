package com.lineacademy.fridgemanagerspring.dto.user.response;


import com.lineacademy.fridgemanagerspring.domain.enums.RoleType;
import com.lineacademy.fridgemanagerspring.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Entity 타입의 객체를 그대로 내밷는 것 자체에도 에러가 발생될 뎌지가 있긴하지만,
// 특히, "password"처럼 response에 포함하지 말아야 되는 정보들을 걸러내리 위해


@Getter
@Builder
public class UserResponse {
    private Long id;
    private String nickname;
    private String email;
    private LocalDate birthdate;
    private RoleType role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // from 메서드를 쓰면 User 타입의 객체를 받아, UserResponse객체로 변환허여 리턴
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .birthdate(user.getBirthdate())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
