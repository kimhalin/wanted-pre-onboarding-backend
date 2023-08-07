package com.example.wantedpreonboardingbackend.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardUpdateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}

