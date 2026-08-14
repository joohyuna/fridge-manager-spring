package com.lineacademy.fridgemanagerspring.repository;

import com.lineacademy.fridgemanagerspring.domain.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 페이지네이션에 적용이 된 목록을 id에 대해 내림차순 정렬한 메서드
    Page<Notice> findAllByOrderByIdDesc(Pageable pageable);
}
