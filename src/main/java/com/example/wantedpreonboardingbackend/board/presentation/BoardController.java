package com.example.wantedpreonboardingbackend.board.presentation;

import com.example.wantedpreonboardingbackend.auth.domain.AuthInfo;
import com.example.wantedpreonboardingbackend.board.application.BoardService;
import com.example.wantedpreonboardingbackend.board.dto.request.BoardCreateRequest;
import com.example.wantedpreonboardingbackend.board.dto.request.BoardUpdateRequest;
import com.example.wantedpreonboardingbackend.board.dto.response.BoardResponse;
import com.example.wantedpreonboardingbackend.global.common.PaginatedRequest;
import com.example.wantedpreonboardingbackend.global.common.PaginatedResponse;
import com.example.wantedpreonboardingbackend.global.support.annotation.CurrentUser;
import com.example.wantedpreonboardingbackend.global.support.annotation.NoAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @NoAuth
    @GetMapping
    @Operation(summary = "게시글 목록 조회")
    public ResponseEntity<PaginatedResponse<BoardResponse>> getAllBoards(PaginatedRequest dto) {
        PaginatedResponse<BoardResponse> result = this.boardService.getAllBoards(dto);
        return ResponseEntity.ok(result);
    }

    @NoAuth
    @GetMapping("/{boardId}")
    @Operation(summary = "게시글 단일 조회")
    public ResponseEntity<BoardResponse> getOneBoard(@PathVariable Long boardId) {
        BoardResponse result = this.boardService.getOneBoard(boardId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{boardId}")
    @Operation(summary = "게시글 수정", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> updateBoard(@PathVariable Long boardId, @RequestBody BoardUpdateRequest dto,
                                            @CurrentUser @Parameter(hidden = true) AuthInfo authInfo) {
        this.boardService.updateBoard(boardId, dto, authInfo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{boardId}")
    @Operation(summary = "게시글 삭제", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId,
                                            @CurrentUser @Parameter(hidden = true) AuthInfo authInfo) {
        this.boardService.deleteBoard(boardId, authInfo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
