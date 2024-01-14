package com.jsj.GTA.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jsj.GTA.service.TouristAttractionsService;
import com.jsj.GTA.config.jwt.JwtTokenProvider;
import com.jsj.GTA.domain.jwt.TokenDto;
import com.jsj.GTA.domain.jwt.TokenResponseDto;
import com.jsj.GTA.domain.users.UsersDto;
import com.jsj.GTA.service.OAuth2UserService;
import com.jsj.GTA.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class ApiController {

    private final TokenService tokenService;
    private final OAuth2UserService oAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    @Operation(summary = "토큰 갱신")
    @PostMapping("/refreshToken")
    public ResponseEntity<String> refreshToken(HttpServletRequest request, @RequestBody String accessToken) {
        LOGGER.info("[refreshToken] request: {} accessToken: {}", request, accessToken);
        String refreshToken = request.getHeader("Authorization").substring(7);
        LOGGER.info("[refreshToken] refreshToken: {}", refreshToken);

        if(!jwtTokenProvider.tokenMatches(accessToken.substring(7), refreshToken)) {
            return ResponseEntity.badRequest().body("두 토큰의 소유주가 일치하지 않습니다.");
        }

        TokenDto tokenDto = tokenService.regenerateToken(refreshToken);
        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", tokenDto.getRefreshToken())
                .httpOnly(true)
                .secure(true).sameSite("None")
                .maxAge(tokenDto.getDuration())
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header("Set-Cookie", responseCookie.toString())
                .header("Authorization", "Bearer " + tokenDto.getAccessToken()).build();
    }

    @Operation(summary = "카카오 소셜 로그인")
    @GetMapping("/kakao")
    public ResponseEntity<TokenResponseDto> oauth2Kakao(@RequestParam("access_token") String accessToken) throws ParseException, JsonProcessingException, ParseException, JsonProcessingException {
        LOGGER.info("[oauth2Kakao] accessToken: {}", accessToken);
        Map<String, Object> usersMap =  oAuth2UserService.findOrSaveUsers(accessToken, "kakao");
        TokenDto tokenDto = tokenService.createToken((UsersDto) usersMap.get("dto"));

        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", tokenDto.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(tokenDto.getDuration())
                .path("/")
                .build();

        TokenResponseDto tokenResponseDto = TokenResponseDto.builder()
                .isNewUser(false)
                .accessToken(tokenDto.getAccessToken())
                .build();

        return ResponseEntity.status((Integer) usersMap.get("status")).header("Set-Cookie", responseCookie.toString()).body(tokenResponseDto);
    }
}