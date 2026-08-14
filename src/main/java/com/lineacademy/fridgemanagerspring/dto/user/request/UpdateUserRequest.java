package com.lineacademy.fridgemanagerspring.dto.user.request;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    @Size(min = 2, max = 10, message = "닉네임은 2자이상 10자 이하여야 합니다.")
    private String nickname;

    private String birthdate;
}
