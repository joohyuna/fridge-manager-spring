package com.lineacademy.fridgemanagerspring.controller;


import com.lineacademy.fridgemanagerspring.domain.notice.Notice;
import com.lineacademy.fridgemanagerspring.dto.common.PaginationResponse;
import com.lineacademy.fridgemanagerspring.dto.notice.response.NoticeResponse;
import com.lineacademy.fridgemanagerspring.repository.NoticeRepository;
import com.lineacademy.fridgemanagerspring.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getNoticeList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
       try {
           // Page라고 하는 객체 안에는 여러가지 정보를 한꺼번에 받아옴
           // totalElement, totalPage 등
           Page<Notice> noticePage = noticeService.getNoticeList(page, size);

           List<NoticeResponse> list = noticePage.getContent().stream()
                   .map(NoticeResponse::from)
                   .toList();

           long total = noticePage.getTotalElements();

           PaginationResponse<NoticeResponse> paginationData = PaginationResponse.of(
                   page,
                   size,
                   total,
                   list
           );

           return ResponseEntity.ok(Map.of(
                   "message", "공지사항 목록을 불러오는데 성공했습니다.",
                   "data", paginationData
           ));

       } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message", "공지사항 목록 조회 중 서버 에러가 발생되었습니다."
            ));
       }
    }
}
