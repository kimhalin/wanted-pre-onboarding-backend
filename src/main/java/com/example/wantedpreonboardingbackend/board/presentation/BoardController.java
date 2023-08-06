package com.example.wantedpreonboardingbackend.board.presentation;

import com.example.wantedpreonboardingbackend.auth.domain.AuthInfo;
import com.example.wantedpreonboardingbackend.board.application.BoardService;
import com.example.wantedpreonboardingbackend.board.dto.request.BoardCreateRequest;
import com.example.wantedpreonboardingbackend.global.support.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/boards")
@Tag(name = "게시글")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    @Operation(summary = "게시글 생성", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> createBoard(@RequestBody BoardCreateRequest dto,
                                            @CurrentUser @Parameter(hidden = true) AuthInfo authInfo) {
        this.boardService.createBoard(dto, authInfo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
