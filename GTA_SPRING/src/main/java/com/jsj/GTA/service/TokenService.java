package com.jsj.GTA.service;

import com.jsj.GTA.config.jwt.JwtTokenProvider;
import com.jsj.GTA.domain.jwt.RefreshTokenRepository;
import com.jsj.GTA.domain.jwt.TokenDto;
import com.jsj.GTA.domain.jwt.RefreshToken;
import com.jsj.GTA.domain.users.Users;
import com.jsj.GTA.domain.users.UsersDto;
import com.jsj.GTA.domain.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UsersRepository usersRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(TouristAttractionsService.class);


    public TokenDto createToken(UsersDto usersDto) {
        LOGGER.info("TokenService[createToken(UserDto)] request data userId: {}", usersDto.getUserId());
        TokenDto tokenDto = tokenProvider.createTokenDto(usersDto.getUserId(), usersDto.getRole());
        Users users = usersRepository.findByUserId(usersDto.getUserId()).orElseThrow(() -> new RuntimeException("Wrong Access (users does not exist)"));
        RefreshToken refreshToken = RefreshToken.createDto(
                tokenDto.getRefreshToken(),
                users.getUserId());

        refreshTokenRepository.save(refreshToken);
        LOGGER.info("TokenService[createToken(UserDto)] save RefreshToken data userId: {} token: {}",
                refreshToken.getUsersId(),
                refreshToken.getToken());
        return tokenDto;
    }

    public TokenDto createToken(Users users) {
        LOGGER.info("TokenService[createToken(Users)] Users data: {} userId: {}",
                users,
                users.getUserId());
        TokenDto tokenDto = tokenProvider.createTokenDto(users.getUserId(), users.getRole().getKey());
        LOGGER.info("TokenService[createToken(Users)] TokenDto data AccessToken: {} TokenType: {} RefreshToken: {} Duration: {}",
                tokenDto.getAccessToken(),
                tokenDto.getTokenType(),
                tokenDto.getRefreshToken(),
                tokenDto.getDuration());
        RefreshToken refreshToken = RefreshToken.builder()
                .usersId(users.getUserId())
                .token(tokenDto.getRefreshToken())
                .build();
        LOGGER.info("TokenService[createToken(Users)] RefreshToken data userId: {} token: {}",
                refreshToken.getUsersId(),
                refreshToken.getToken());
        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }

    public TokenDto regenerateToken(String refreshToken) {
        LOGGER.info("TokenService[regenerateToken] regenerateToken Request data refreshToken: {}",
                refreshToken);
        Optional<RefreshToken> redisRefreshToken = refreshTokenRepository.findById(refreshToken);
        if (!redisRefreshToken.isPresent()) {
            LOGGER.info("TokenService[regenerateToken] RedisRefreshToken doesn't existed");
            return null;
        }
        LOGGER.info("TokenService[regenerateToken] RedisRefreshToken existed");
        String userId = redisRefreshToken.get().getUsersId();
//        refreshTokenRepository.deleteById(refreshToken);
        LOGGER.info("TokenService[regenerateToken] userId: {}", userId);
        Optional<Users> usersEntity = usersRepository.findByUserId(userId);
        if (usersEntity.isPresent()) {
            TokenDto dto = createToken(usersRepository.findByUserId(userId).get());
            return dto;
        } else {
            LOGGER.info("TokenService[regenerateToken] usersEntity doesn't existed");
            return null;
        }
    }
}