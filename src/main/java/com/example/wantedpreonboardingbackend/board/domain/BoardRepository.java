package com.example.wantedpreonboardingbackend.board.domain;

import com.example.wantedpreonboardingbackend.global.exception.ErrorMessage;
import com.example.wantedpreonboardingbackend.global.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findById(final Long id);
    Page<Board> findAll(Pageable pageable);

    default Board getById(final Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ERROR_BOARD_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
