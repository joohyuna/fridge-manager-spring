package com.lineacademy.fridgemanagerspring.domain.common;

// 모든 데이터 모델이 갖도록 하는 베이스 Entity

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter // 클래스 내부에 모든 멤버변수들의 Getter 메서드를 자동 생성
// Mapping : ORM에서 테이블과 클래스(모델)을 연결짓는 과정
// 그러한 맵핑을 할 때 기반이 되는 자식 클래스들의 부모로서 작용한다고 지정
// Entity -> 테이블과 연결되는 클래스들을 지칭
// MappedSuperclass는 DB와 연관성을 갖는 클래스는 맞지만, 이걸로 테이블 연결은 안하겠다
@MappedSuperclass
// 엔티티가 기록되는 등의 이벤트를 감지하여 자동 업데이트를 진행해주기 위함
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedDate // 이 엔티티가 처음 생성되어 저장될 때 생성 시간을 자동으로 저장
    @Column(updatable = false, name = "created_at")  // 이 변수가 DB 칼럼 데이터 지정, updatable = false를 통해 생성 이후 업데이트 불가 처리
    private LocalDateTime createdAt;

    @LastModifiedDate  // 엔티티의 값이 변경될 때마다 자동으로 시간을 업데이트
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // 참조자료형이라는 뜻 LocalDateTime 기본적으로 null 이 포함 되어져 있다

    // deletedAt처럼, soft delete를 하기 위한 칼럼은 어노테이션이 없음. 수동 메서드를 구현해줬음
    public void markAsDeleted() {
        this.deletedAt = LocalDateTime.now();
    }
}
