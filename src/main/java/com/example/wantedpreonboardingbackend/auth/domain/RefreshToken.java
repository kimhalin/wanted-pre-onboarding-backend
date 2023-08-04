package com.example.wantedpreonboardingbackend.auth.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Table(name = "refresh_tokens")
@Entity
@Builder
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "refresh_token", nullable = false)
    private String token;

    public void change(final String refreshToken) {
        if (!Objects.isNull(refreshToken)) {
            this.token = refreshToken;
        }
    }
}
