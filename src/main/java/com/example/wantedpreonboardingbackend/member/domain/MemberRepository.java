package com.example.wantedpreonboardingbackend.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final String email);
    Optional<Member> findById(final Long id);

    boolean existsByEmail(final String email);
}
