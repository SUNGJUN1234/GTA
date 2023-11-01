package com.jsj.GTA.domain.stamps;

import lombok.Getter;

@Getter
public class UsersStampCountDto {
    private Long id;
    private int count;

    public UsersStampCountDto(Long id, int count) {
        this.id = id;
        this.count = count;
    }
}
