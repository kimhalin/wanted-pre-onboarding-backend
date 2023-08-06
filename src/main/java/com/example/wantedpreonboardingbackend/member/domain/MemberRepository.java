package com.example.wantedpreonboardingbackend.member.domain;

import com.example.wantedpreonboardingbackend.global.exception.ErrorMessage;
import com.example.wantedpreonboardingbackend.global.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final String email);
    Optional<Member> findById(final Long id);

    boolean existsByEmail(final String email);

    default Member getById(final Long id) {
        return findById(id)
                .orElseThrow(()-> new NotFoundException(ErrorMessage.ERROR_MEMBER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    default Member getByEmail(final String email) {
        return findByEmail(email)
                .orElseThrow(()-> new NotFoundException(ErrorMessage.ERROR_MEMBER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }


}
