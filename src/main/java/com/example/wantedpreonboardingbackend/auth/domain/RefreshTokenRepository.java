package com.example.wantedpreonboardingbackend.auth.domain;

import com.example.wantedpreonboardingbackend.global.exception.ErrorMessage;
import com.example.wantedpreonboardingbackend.global.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByMemberId(Long memberId);
    Optional<RefreshToken> findById(Long id);

    boolean existsByMemberId(Long memberId);

    default RefreshToken getByMemberId(final Long memberId) {
        return findByMemberId(memberId)
                .orElseThrow(()-> new NotFoundException(ErrorMessage.ERROR_TOKEN_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    default RefreshToken getById(final Long id) {
        return findById(id)
                .orElseThrow(()-> new NotFoundException(ErrorMessage.ERROR_TOKEN_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
