package com.lineacademy.fridgemanagerspring.controller.admin;

import com.lineacademy.fridgemanagerspring.domain.notice.Notice;
import com.lineacademy.fridgemanagerspring.dto.notice.request.NoticeRequest;
import com.lineacademy.fridgemanagerspring.dto.notice.response.NoticeResponse;
import com.lineacademy.fridgemanagerspring.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/notice")
@RequiredArgsConstructor
public class AdminNoticeController {
    private final NoticeService noticeService;


    // Mapping 정보에 주소를 안 찍으면, Controller에 기재됨
    // 주소로 들어왔을 때 메서드
    //  @PreAuthorize("isAuthorized()")  모든 사람이 가능
    @PreAuthorize("hasRole('ADMIN')") // 매개변수 자리에 "String"을 써주고 있음 관리자만
    @PostMapping
    public ResponseEntity<Map<String, Object>> createNotice(
            @Valid @RequestBody NoticeRequest request

            ) {
        try {
            Notice notice = noticeService.createNotice(request);

            return ResponseEntity.ok(Map.of(
                    "message", "공지사항이 정상적으로 등록되었습니ㅏㄷ.",
                    "data", NoticeResponse.from(notice)
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message", "서버에러가 발생되었습니다"
            ));
        }
    }
}
