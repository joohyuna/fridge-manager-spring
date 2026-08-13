package com.lineacademy.fridgemanagerspring.controller;


import com.lineacademy.fridgemanagerspring.domain.user.User;
import com.lineacademy.fridgemanagerspring.dto.user.LoginRequest;
import com.lineacademy.fridgemanagerspring.dto.user.request.CreateUserRequest;
import com.lineacademy.fridgemanagerspring.dto.user.response.UserResponse;
import com.lineacademy.fridgemanagerspring.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController  // 이 클레스가 웹서비스를 할 때 이 용되는 컨트롤러임을 명시
@RequestMapping("/users")  // /users 라는 주소로 Request가 오면 이 컨트롤러에 도달
@RequiredArgsConstructor  // 매개변수 생성자를 자동으로 생성해주는 에노테이션
                     // final 필드나 @NonNull 필드가 뽑은 것들을 매개변수로 한
public class UserController {
    // 맴버 변수
    private final UserService userService;  // Java 에서는 객체를 만들어야 실행을 할수 있다



    // 맴버 메서드
    @PostMapping("/create")  // class의 매핑정보인 "/users" 뒤에 "/create"가 붙고, POST방식이면 이 메서드 실행
    // ResponseEntity : Spring-Boot Web Service에서 응답을 정의하는 타입
    // T 자리에는 response.body(실제 내용이 기록되는 편지지)의 타입에 들어가야 함
    public ResponseEntity<Map<String, Object>> createUser(
            // Spring-Boot에서는 컨트롤러의 메서드를 실행할 때,
            // 자동으로 req.body값이 매개변수로 들어옴

            // @Valid는 이 매개변수에 대한 검증 절차를 실행할 것이고, 실패하면, GlobalExceptionHandler로
            @Valid @RequestBody CreateUserRequest request
    ) {
        try {
            // 서비스에 request를 그대로 넘겨서, 생성 요청을 할 것이고
            // 서비스는 생성이 끝난 결과 (생성 '된' User  객체)를 리턴하게 만들것임
            User user = userService.createUser(request);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "성공적으로 회원가입 되었습니다.",
                            "data", UserResponse.from(user)
                    ));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("ALREADY_EXISTS_EMAIL"))
                return ResponseEntity.status((HttpStatus.CONFLICT))
                        .body(Map.of(
                                "message", "이미 가입된 메일입니다."
                        ));
            if (e.getMessage().equals("ALREADY_EXISTS_NICKNAME"))
                return ResponseEntity.status((HttpStatus.CONFLICT))
                        .body(Map.of(
                                "message", "이미 사용중인 닉네임입니다."
                        ));
            return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR))
                    .body(Map.of(
                            "message", "서버 에러가 발생되었습니다."
                    ));

        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @Valid @RequestBody LoginRequest request
    ) {
       try {
           // 1. 사용자가 입력해온 값을 DB에서 조회에서 있는 확인
           User user = userService.login(request);

           //2. 토큰생성 해서 response 전달
       } catch () {

       }
    }
}
