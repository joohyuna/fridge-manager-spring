package com.lineacademy.fridgemanagerspring.repository;

import com.lineacademy.fridgemanagerspring.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//  JpaRepository <엔티티 클래스, Pk타입>을 상속함
public interface UserRepository extends JpaRepository<User, Long> {
    // String 이 메일 통해 검색해오는 메서드
    // 첫번째 형태
    Optional<User> findByEmail(String email);

    // Nickname을 기준으로 존재여부르 ㄹ확인하는 메서드
    // 두변째형태 exist로 하면 SELECT count(*) WHERE nickname = ""   => 없으면 0, 있으면 양수가 나옴 => 0일때 false, 양수일때 true
    // 이름을 적는대로 해석해서 만들어준다 Hibernate Docs : PDF 482페이지
    boolean existsByNickname(String nickname);

    // 내 ID를 제외하고 해당 닉네임이 존재하는지 검사하는 메서드
    boolean existsByNicknameAndIdNot(String nickname, Long id);
}
