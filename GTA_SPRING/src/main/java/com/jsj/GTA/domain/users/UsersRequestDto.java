package com.jsj.GTA.domain.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersRequestDto {

    private String userId;
    private String name;
    private String password;
    private String nickname;
    private String email;

    public void updatePassword(String password) {
        this.password = password;
    }

    public Users toEntity(UsersRequestDto dto) {
        return new Users(
                dto.userId,
                dto.password,
                dto.nickname,
                dto.email,
                null,
                null,
                Role.USER,
                new Date(),
                null,
                Status.NORMAL
        );
    }
}