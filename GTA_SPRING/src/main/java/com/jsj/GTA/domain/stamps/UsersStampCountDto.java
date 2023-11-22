package com.jsj.GTA.domain.stamps;

import lombok.Getter;

@Getter
public class UsersStampCountDto {
    private Long id;
    private Long count;

    public UsersStampCountDto(Long id, Long count) {
        this.id = id;
        this.count = count;
    }
}
