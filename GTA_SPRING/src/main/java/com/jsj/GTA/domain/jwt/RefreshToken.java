package com.jsj.GTA.domain.jwt;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Getter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 30 * 24 * 60 * 60 * 1000L)
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class RefreshToken {

    @Id
    private String token;
    @Indexed
    private String usersId;

    public RefreshToken updateValue(String token) {
        this.token = token;
        return this;
    }

    public static RefreshToken createDto(String token, String usersId) {
        RefreshToken refreshToken = new RefreshToken(
                token,
                usersId
        );
        return refreshToken;
    }
}
