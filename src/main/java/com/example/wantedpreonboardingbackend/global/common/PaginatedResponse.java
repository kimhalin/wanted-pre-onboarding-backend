package com.example.wantedpreonboardingbackend.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> items;

    private int totalPages;
    private Long totalElements;
    private int pageSize;
    private int pageNumber;
    private Long offset;
}
