package com.example.wantedpreonboardingbackend.global.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CryptoEncoder {

    public static String encrypt(final String value) {
        final MessageDigest messageDigest = getMessageDigestInstance();
        final byte[] hash = messageDigest.digest(value.getBytes(StandardCharsets.UTF_8));

        return bytesToHexString(hash);
    }

    private static String bytesToHexString(final byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        for (final byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    private static MessageDigest getMessageDigestInstance() {
        try {
            return MessageDigest.getInstance("SHA3-256");
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
