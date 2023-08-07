package com.example.wantedpreonboardingbackend.global.common;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Setter
public class PaginatedRequest {
    private int page;
    private int size;

    private String orderBy = "createdAt";
    private Sort.Direction direction = Sort.Direction.DESC;

    public PageRequest toPageRequest() {
        return PageRequest.of(this.page, this.size, this.direction, this.orderBy);
    }
}
