package com.example.wantedpreonboardingbackend.board.dto.response;

import com.example.wantedpreonboardingbackend.board.domain.Board;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BoardResponse {

    private Long id;

    private String title;

    private String content;

    public static BoardResponse of(Board board) {
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}
