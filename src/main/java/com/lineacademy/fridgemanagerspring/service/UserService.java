package com.lineacademy.fridgemanagerspring.service;

import com.lineacademy.fridgemanagerspring.domain.fridge.Fridge;
import com.lineacademy.fridgemanagerspring.domain.user.User;
import com.lineacademy.fridgemanagerspring.dto.user.request.LoginRequest;
import com.lineacademy.fridgemanagerspring.dto.user.request.CreateUserRequest;
import com.lineacademy.fridgemanagerspring.dto.user.request.UpdateUserRequest;
import com.lineacademy.fridgemanagerspring.repository.FridgeRepository;
import com.lineacademy.fridgemanagerspring.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor  //매개변수를 생성하는 어노테이션
public class UserService {
    private final UserRepository userRepository;
    private final FridgeRepository fridgeRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional   // 진행 중간에 에러가 만약 발생되면  DB 작업할 것을 콜백하는 이노베이션
    public User createUser(@Valid CreateUserRequest request) {
        // 우리가 Express를 사용라 때에는 schema.prisma만 만들고,
        // generate를 실행하면 우리가 사용할 수 있는 모든 메서드가 작성되엉 만들어지고 사용만 했으면 됨

        // Spring-Boot , Hibernate에서는 매세드를 자동으로 만들어 주는건 finnById (PrimaryKey를 대상으로 검색)만 있고
        // 나머지는 직접 만들어야 함, 대신, "메서드명"을 작성하면 해당 코드는 알아서 만들어짐

        // 이러한 구조에서 데이터베이스 등의 저장소에 접근하는 기능 모믐 클래스를 "레포지토리( repository)"라고 함

       // 이메일 중복 체크 => 이에일로 검색 이것은 만들어줘야 함
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("ALREADY_EXISTS_EMAIL");
        }

        // 닉네임 중복 체크 => 닉네임으로 정보
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("ALREADY_EXISTS_NICKNAME");
        }

        // 입력된 birthdate는 String이니까 이걸 LocalDate 객체로 변환
        LocalDate parsedBirthdate = null;
        if (request.getBirthdate() != null && !request.getBirthdate().isBlank()) {
            parsedBirthdate = LocalDate.parse(request.getBirthdate(), DateTimeFormatter.ISO_DATE);
        }

        // 사용자 정보를 데이터 베이스 저장
        // 1. 사용자 저장
        // user 변수에 저장된 객체는 repository
        User user = User.builder()
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .birthdate(parsedBirthdate)
                .build();
        userRepository.save(user);

        // 2. 기본 냉장고 저장
        Fridge defaultFridge = Fridge.builder()
                .name("내 냉장고")
                .user(user)
                .build();
        fridgeRepository.save(defaultFridge);

        return user;
    }

    @Transactional
    public User login(LoginRequest request) {
        // 1. 받아오느 email값을 통해 사용자가 있는 확인하고
        // 함수처럼 만들어서 쓸수 있는게 Java에서 지원되지만 함수는 아니고
        // 람다 표현식() ->
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("INVALID_CREDENTIALS"));

        // 2. 사용자가 존재한다면, 탈퇴된 회원인지를 검사하고
        if (user.getCreatedAt() != null) {
            throw new RuntimeException("INVALID_CREDENTIALS");
        }

        // 3. 비밇번호가 일치하는데 확인하고
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException(("INVALID_CREDENTIALS"));
        }

        return user;
    }

    @Transactional
    public User updateUser(Long currentUserId, @Valid UpdateUserRequest request) {
        // 1. 사용자가 존재하는 지
        // 여기 user 변수에 저장이 된 User Entity 객체는
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        // 2. 탈퇴는 하지 않았는지
        if (user.getDeletedAt() != null) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        // 3. 변경하려는 닉네임이 겹치지 않는지
        if (request.getNickname() != null) {
            if (userRepository.existsByNicknameAndIdNot(request.getNickname(), currentUserId)) {
                throw new RuntimeException("DUPLICATED_NICKNAME");
            }
            user.updateNickname(request.getNickname());
        }
        // 4. birth
        if (request.getNickname() != null && !request.getBirthdate().isBlank()) {
            LocalDate parsedBirthdate = LocalDate.parse(request.getBirthdate(), DateTimeFormatter.ISO_DATE);
            user.updateBirthdate(parsedBirthdate); // 변수의 값을 변경
        }

        //  디비에 쓰지 않고, 리턴으로 끝났음
        return user;
        // repository에서 가져온 내용을 할당된 user객체의 값이 변경된 상태에서 메서드 실행이 끝나면
        // 자동으로 디비믜 값도 업데이트 함 => Dirty Check
    }

}
