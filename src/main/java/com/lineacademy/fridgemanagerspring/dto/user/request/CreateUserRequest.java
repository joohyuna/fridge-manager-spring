package com.lineacademy.fridgemanagerspring.dto.user.request;


// Data Transfer Object > 데이터 전달 객체
// 매핑 클래스 // 비슷한 개념으로서 zod의 ,schema

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, message = "비밀번호는 6자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "사용자명은 필수입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하여야 합니다.")
    private String nickname;

    @NotBlank(message = "사용자명은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아님니다. ")
    private String email;

    private String birthdate;
}
