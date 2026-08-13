package com.lineacademy.fridgemanagerspring.domain.user;

import com.lineacademy.fridgemanagerspring.domain.category.Category;
import com.lineacademy.fridgemanagerspring.domain.common.BaseTimeEntity;
import com.lineacademy.fridgemanagerspring.domain.enums.RoleType;
import com.lineacademy.fridgemanagerspring.domain.fridge.Fridge;
import com.lineacademy.fridgemanagerspring.domain.inquiry.Inquiry;
import com.lineacademy.fridgemanagerspring.domain.shoppinglist.ShoppingList;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity // DB 테이블과 매핑되는 JPA 엔티티 클래스임을 선언
@Table(name = "user") // 매핑될 DB 테이블 이름을 "user"로 지정
@Getter
// 어노테이션 @Setter 는 사용 하지 않는다
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @NoArgsContructor()라고만 쓰면 기본 생성자 만들어주는 어노테이션
// 기본 생성자에 접근제한자를 protected로 만들어달라
// 엔티티는 기본 생성자를 protected로 해줘야 함
public class User extends BaseTimeEntity {
    @Id  // 테이블의 Primary Key를 지정
    // 기록되는 값이 자동으로 생성되는데 AUTO_INCREMENT 되도록 생성
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // DB에 Int 타입은 Java에서는 Long

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column()
    private LocalDateTime birthdate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role = RoleType.USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ShoppingList> shoppingLists = new ArrayList<>();

    // mappedBy = "user" 는 Fridge 엔티티에 만들어질 user 필드명
    // cascade는, 연결지은 이 user가 삭제될 경우 연결될 fridge는 어떻게 할까?
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Fridge> fridges = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Inquiry> inquiries = new ArrayList<>();

    @Builder
    private User(String nickname, String password, String email, LocalDateTime birthdate, RoleType role) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.birthdate = birthdate;
        if (role != null) this.role = role; // role은 enum 타입이라서
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }  // setter 을 update로 넣고 사용

    public void updateBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}