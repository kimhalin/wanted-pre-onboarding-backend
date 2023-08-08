package com.example.wantedpreonboardingbackend.board.application;

import com.example.wantedpreonboardingbackend.auth.domain.AuthInfo;
import com.example.wantedpreonboardingbackend.board.domain.Board;
import com.example.wantedpreonboardingbackend.board.domain.BoardRepository;
import com.example.wantedpreonboardingbackend.board.dto.request.BoardCreateRequest;
import com.example.wantedpreonboardingbackend.board.dto.request.BoardUpdateRequest;
import com.example.wantedpreonboardingbackend.board.dto.response.BoardResponse;
import com.example.wantedpreonboardingbackend.global.common.PaginatedRequest;
import com.example.wantedpreonboardingbackend.global.common.PaginatedResponse;
import com.example.wantedpreonboardingbackend.global.exception.BusinessException;
import com.example.wantedpreonboardingbackend.global.exception.ErrorMessage;
import com.example.wantedpreonboardingbackend.global.exception.NotFoundException;
import com.example.wantedpreonboardingbackend.member.application.MemberService;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    @Transactional
    public void createBoard(final BoardCreateRequest dto, final AuthInfo authInfo) {
        Member member = this.memberService.getById(authInfo.getId());

        Board board = dto.toEntity(member);
        this.boardRepository.save(board);
    }

    public BoardResponse getOneBoard(final Long boardId) {
        Board board = this.getById(boardId);
        return BoardResponse.of(board);
    }

    @Transactional
    public void updateBoard(final Long boardId, final BoardUpdateRequest dto, final AuthInfo authInfo) {
        Board board = this.getById(boardId);
        Long authInfoUserId = authInfo.getId();

        // 작성자인지 확인
        checkAuthor(authInfoUserId, board.getMember().getId());

        board.update(dto.getTitle(), dto.getContent());
    }

    public PaginatedResponse<BoardResponse> getAllBoards(final PaginatedRequest dto) {
        Pageable pageable = dto.toPageRequest();
        Page<Board> boards = this.boardRepository.findAll(pageable);
        List<BoardResponse> boardResponseList = boards.getContent().stream()
                .map(BoardResponse::of).toList();

        return new PaginatedResponse<>(boardResponseList, boards.getTotalPages(), boards.getTotalElements(), boards.getPageable().getPageSize(),
                boards.getPageable().getPageNumber(), boards.getPageable().getOffset());

    }

    @Transactional
    public void deleteBoard(Long boardId, AuthInfo authInfo) {
        Board board = this.getById(boardId);
        Long authInfoUserId = authInfo.getId();

        // 작성자인지 확인
        checkAuthor(authInfoUserId, board.getMember().getId());

        this.boardRepository.delete(board);
    }

    public Board getById(Long boardId) {
        return this.boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ERROR_BOARD_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private void checkAuthor(Long authInfoUserId, Long boardUserId) {
        if (!authInfoUserId.equals(boardUserId)) {
            throw new BusinessException(ErrorMessage.ERROR_ONLY_AUTHOR_CAN_HANDLE, HttpStatus.CONFLICT);
        }
    }
}
