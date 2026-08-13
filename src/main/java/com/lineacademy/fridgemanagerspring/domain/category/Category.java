package com.lineacademy.fridgemanagerspring.domain.category;

import com.lineacademy.fridgemanagerspring.domain.common.BaseTimeEntity;
import com.lineacademy.fridgemanagerspring.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String icon = "tag";

    // Java의 ORM(데이터베이스 관계 라이브러리) 들은
    // 무조건 데이터베이스에 사용하는 명칭들을
    // 소문자와 어너바를 이용해서 만듦
    // ex. isDefault -> is_default
    // 그러다 보니 다른 언어를 사용해서 만드는 프로젝트드레도
    // 그 관례를 따르는 편
    // Java에서는 isDefault 를 자동으로 is_default 만듬ㄹ
    @Column(nullable = false, name = "is_default")
    private Boolean isDefault;


    // TODO: Product 테이블과의 관계 설정


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Category(String name, String icon, Boolean isDefault, User user) {
        this.name = name;
        this.icon = icon;
        this.isDefault = isDefault;
        this.user = user;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
