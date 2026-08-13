package com.lineacademy.fridgemanagerspring.repository;

import com.lineacademy.fridgemanagerspring.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//  JpaRepository <엔티티 클래스, Pk타입>을 상속함
public interface UserRepository extends JpaRepository<User, Long> {
    // String 이 메일 통해 검색해오는 메서드
    Optional<User> findByEmail(String email);

    // Nickname을 기준으로 존재여부르 ㄹ확인하는 메서드
    boolean existsByNickname(String nickname);

    // 내 ID를 제외하고 해당 닉네임이 존재하는지 검사하는 메서드
    boolean existsByNicknameAndIdNot(String nickname, Long id);
}
