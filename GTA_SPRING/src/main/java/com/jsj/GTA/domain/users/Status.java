package com.jsj.GTA.domain.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    DELETE("DELETE", "삭제"),
    NORMAL("NORMAL", "일반"),
    DORMANCY("DORMANCY", "휴면");

    private final String key;
    private final String title;
}
