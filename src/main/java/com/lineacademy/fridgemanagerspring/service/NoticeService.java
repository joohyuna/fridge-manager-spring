package com.lineacademy.fridgemanagerspring.service;

import com.lineacademy.fridgemanagerspring.domain.notice.Notice;
import com.lineacademy.fridgemanagerspring.dto.notice.request.NoticeRequest;
import com.lineacademy.fridgemanagerspring.repository.NoticeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @Transactional
    public Page<Notice> getNoticeList(int page, int size) {
        Pageable pageable = PageRequest.of(page -1, size);
        return noticeRepository.findAllByOrderByIdDesc(pageable);
    }

    @Transactional
    public Notice getNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_NOTICE"));
    }

    @Transactional
    public Notice createNotice(@Valid NoticeRequest request) {
        Notice notice = Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return noticeRepository.save(notice);
    }

    // 진짜 삭제하는 메서드
    @Transactional
    public void deleteNotice(Long noticeId) {
        Notice notice = getNoticeById(noticeId);
        if (notice != null) {
            noticeRepository.delete(notice);
        } else {
            throw  new IllegalArgumentException("NOT_FOUND_NOTICE");
        }

    }
}
