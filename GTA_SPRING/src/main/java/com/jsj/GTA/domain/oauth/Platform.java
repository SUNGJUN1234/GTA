package com.jsj.GTA.domain.oauth;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Platform {
    GOOGLE("google"),
    KAKAO("kakao"),
    NAVER("naver");

    private final String platform;

    public static Platform of(String provider) {
        switch (provider) {
            case "google":
                return Platform.GOOGLE;
            case "kakao":
                return Platform.KAKAO;
            case "naver":
                return Platform.NAVER;
            default:
                return null;
        }
    }
}