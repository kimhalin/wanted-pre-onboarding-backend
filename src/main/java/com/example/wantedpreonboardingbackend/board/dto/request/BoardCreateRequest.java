package com.example.wantedpreonboardingbackend.board.dto.request;

import com.example.wantedpreonboardingbackend.board.domain.Board;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BoardCreateRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public Board toEntity(Member member) {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .member(member)
                .build();
    }
}
