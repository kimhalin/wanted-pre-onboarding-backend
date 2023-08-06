package com.example.wantedpreonboardingbackend.board.application;

import com.example.wantedpreonboardingbackend.auth.domain.AuthInfo;
import com.example.wantedpreonboardingbackend.board.domain.Board;
import com.example.wantedpreonboardingbackend.board.domain.BoardRepository;
import com.example.wantedpreonboardingbackend.board.dto.request.BoardCreateRequest;
import com.example.wantedpreonboardingbackend.member.application.MemberService;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    public void createBoard(BoardCreateRequest dto, AuthInfo authInfo) {
        Member member = this.memberService.getById(authInfo.getId());

        Board board = dto.toEntity(member);
        this.boardRepository.save(board);
    }
}
