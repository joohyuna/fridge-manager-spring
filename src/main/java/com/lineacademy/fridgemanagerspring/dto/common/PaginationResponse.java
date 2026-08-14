package com.lineacademy.fridgemanagerspring.dto.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class PaginationResponse<T> {
    private int page;
    private int size;
    private long total;
    private List<T> list;

    public static <T> PaginationResponse<T> of(int page, int size, long total, List<T> list) {
         return PaginationResponse.<T>builder()
                 .page(page)
                 .size(size)
                 .total(total)
                 .list(list)
                 .build();

    }
}

// 우리가 Map.of("message", "메세지내용", "data", "어쩌구") 메세지를 사용해서
//  새로운 객체를 만들어냈었던 것 처럼
// PaginationResponse.of<T> 를 통해 PaginationResponse 객체를 만들어내기 위함
