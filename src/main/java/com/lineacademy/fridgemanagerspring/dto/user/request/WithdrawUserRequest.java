package com.lineacademy.fridgemanagerspring.dto.user.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawUserRequest {
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    private String password;

}
