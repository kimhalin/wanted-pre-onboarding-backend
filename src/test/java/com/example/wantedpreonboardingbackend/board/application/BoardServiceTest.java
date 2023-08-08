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
import com.example.wantedpreonboardingbackend.member.application.MemberService;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private MemberService memberService;

    private Member member;
    private Board board1;
    private Board board2;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .id(1L)
                .email("user@example.com")
                .password(Password.of("password"))
                .build();

        board1 = Board.builder()
                .id(1L)
                .member(member)
                .title("게시글 제목")
                .content("게시글 내용")
                .build();
        board2 = Board.builder()
                .id(2L)
                .member(member)
                .title("게시글 제목2")
                .content("게시글 내용2")
                .build();
    }


    @Test
    @DisplayName("게시글을 작성할 수 있다")
    void createBoard() {

        // given
        BoardCreateRequest request = BoardCreateRequest.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .build();
        AuthInfo authInfo = new AuthInfo(member.getId());

        when(memberService.getById(authInfo.getId())).thenReturn(member);
        when(boardRepository.save(any(Board.class))).thenReturn(null);

        // when
        boardService.createBoard(request, authInfo);

        // then
        verify(memberService, times(1)).getById(authInfo.getId());
        verify(boardRepository, times(1)).save(any(Board.class));
    }

    @Test
    @DisplayName("게시글 단일 조회를 할 수 있다")
    void getOneBoard() {

        // given
        when(boardRepository.findById(1L)).thenReturn(Optional.ofNullable(board1));

        // when
        BoardResponse result = boardService.getOneBoard(1L);

        // then
        assertThat(result.getContent()).isEqualTo(board1.getContent());
        assertThat(result.getTitle()).isEqualTo(board1.getTitle());
    }


    @Test
    @DisplayName("게시글 목록 조회를 할 수 있다")
    void getAllBoards() {

        // given
        PaginatedRequest request = PaginatedRequest.builder()
                .page(0)
                .size(3)
                .direction(Sort.Direction.ASC)
                .orderBy("createdAt")
                .build();

        PageRequest pageRequest = request.toPageRequest();
        Page<Board> boards = new PageImpl<>(List.of(board1, board2), pageRequest, 2);

        when(boardRepository.findAll(any(Pageable.class))).thenReturn(boards);

        // when
        PaginatedResponse<BoardResponse> result = boardService.getAllBoards(request);

        // then
        assertAll(
                () -> assertThat(result.getPageNumber()).isZero(),
                () -> assertThat(result.getPageSize()).isEqualTo(3),
                () -> assertThat(result.getTotalPages()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("게시글 작성자가 아닌 사용자가 게시글을 삭제하려는 경우 예외가 발생한다")
    void deleteBoard() {

        // given
        Member member = Member.builder()
                .id(2L)
                .email("user2@example.com")
                .password(Password.of("password"))
                .build();
        AuthInfo authInfo = new AuthInfo(member.getId());

        when(boardRepository.getById(board1.getId())).thenReturn(board1);

        // when & then
        assertThatThrownBy(() -> boardService.deleteBoard(board1.getId(), authInfo))
                .isInstanceOf(BusinessException.class);

    }


    @Test
    @DisplayName("게시글 작성자가 아닌 사용자가 게시글을 수정하려는 경우 예외가 발생한다")
    void updateBoard() {

        // given
        Member member = Member.builder()
                .id(2L)
                .email("user2@example.com")
                .password(Password.of("password"))
                .build();
        BoardUpdateRequest request = BoardUpdateRequest.builder()
                .title("수정 제목")
                .content("수정 내용")
                .build();
        AuthInfo authInfo = new AuthInfo(member.getId());

        when(boardRepository.getById(board1.getId())).thenReturn(board1);

        // when & then
        assertThatThrownBy(() -> boardService.updateBoard(board1.getId(), request, authInfo))
                .isInstanceOf(BusinessException.class);
    }

}