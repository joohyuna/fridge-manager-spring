package com.lineacademy.fridgemanagerspring.domain.shoppinglist;


import com.lineacademy.fridgemanagerspring.domain.common.BaseTimeEntity;
import com.lineacademy.fridgemanagerspring.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shopping_list")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShoppingList  extends BaseTimeEntity {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memo;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, name = "is_checked")
    private Boolean isChecked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public ShoppingList(String memo, LocalDateTime date, Boolean isChecked) {
        this.memo = memo;
        this.date = date;
        this.isChecked = isChecked;
    }

    public void toggleChecked() {
        this.isChecked = !this.isChecked;
    }
}
