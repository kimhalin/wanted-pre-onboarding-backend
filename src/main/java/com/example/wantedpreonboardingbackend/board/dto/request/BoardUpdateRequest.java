package com.example.wantedpreonboardingbackend.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BoardUpdateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}

