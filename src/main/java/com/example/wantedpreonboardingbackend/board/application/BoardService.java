package com.example.wantedpreonboardingbackend.board.application;

import com.example.wantedpreonboardingbackend.auth.domain.AuthInfo;
import com.example.wantedpreonboardingbackend.board.domain.Board;
import com.example.wantedpreonboardingbackend.board.domain.BoardRepository;
import com.example.wantedpreonboardingbackend.board.dto.request.BoardCreateRequest;
import com.example.wantedpreonboardingbackend.board.dto.response.BoardResponse;
import com.example.wantedpreonboardingbackend.global.common.PaginatedRequest;
import com.example.wantedpreonboardingbackend.global.common.PaginatedResponse;
import com.example.wantedpreonboardingbackend.member.application.MemberService;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    public void createBoard(final BoardCreateRequest dto, final AuthInfo authInfo) {
        Member member = this.memberService.getById(authInfo.getId());

        Board board = dto.toEntity(member);
        this.boardRepository.save(board);
    }

    public PaginatedResponse<BoardResponse> getAllBoards(final PaginatedRequest dto) {
        Pageable pageable = dto.toPageRequest();
        Page<Board> boards = this.boardRepository.findAll(pageable);
        List<BoardResponse> boardResponseList = boards.getContent().stream()
                .map(BoardResponse::of).toList();

        return new PaginatedResponse<>(boardResponseList, boards.getTotalPages(), boards.getTotalElements(), boards.getPageable().getPageSize(),
                boards.getPageable().getPageNumber(), boards.getPageable().getOffset());

    }
}
