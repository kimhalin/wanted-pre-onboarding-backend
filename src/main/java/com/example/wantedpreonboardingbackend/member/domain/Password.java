package com.example.wantedpreonboardingbackend.member.domain;

import com.example.wantedpreonboardingbackend.global.support.CryptoEncoder;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    @Column(name = "password", nullable = false)
    private String value;

    public Password(String value) {
        this.value = value;
    }

    public boolean isSamePassword(final String value) {
        final String encodedValue = CryptoEncoder.encrypt(value);
        return value.equals(encodedValue);
    }

    public static Password of(String password) {
        return new Password(CryptoEncoder.encrypt(password));
    }
}
