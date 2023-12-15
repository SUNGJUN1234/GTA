package com.jsj.GTA.config.jwt;

import com.jsj.GTA.api.touristAttractions.TouristAttractionsService;
import com.jsj.GTA.config.auth.SecurityUtil;
import com.jsj.GTA.service.TokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;

@Component
public class JwtTokenProvider {

    private final Key encodedKey;
    private static final String BEARER_TYPE = "Bearer";
    private static final Long accessTokenValidationTime = 24 * 60 * 60 * 1000L;
    private static final Long refreshTokenValidationTime = 7 * 24 * 60 * 60 * 1000L;

    private final Logger LOGGER = LoggerFactory.getLogger(TouristAttractionsService.class);

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.encodedKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * accessToken 과 refreshToken 생성
     *
     * @param subject
     * @return TokenDto
     * subject 는 Form Login 방식의 경우 userId, Social Login 방식의 경우 email
     */
    public TokenDto createTokenDto(String subject, String role) {

        // 토큰 생성시간
        Instant now = Instant.from(OffsetDateTime.now());

        // accessToken 생성
        String accessToken = Jwts.builder()
                .setSubject(subject)
                .claim("role", role)
                .setExpiration(Date.from(now.plusMillis(accessTokenValidationTime)))
                .signWith(encodedKey)
                .compact();

        // refreshToken 생성
        String refreshToken = Jwts.builder()
                .setExpiration(Date.from(now.plusMillis(refreshTokenValidationTime)))
                .signWith(encodedKey)
                .compact();

        //TokenDto 에 두 토큰을 담아서 반환
        return TokenDto.builder()
                .tokenType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .duration(Duration.ofMillis(refreshTokenValidationTime))
                .build();
    }

    /**
     * UsernamePasswordAuthenticationToken 으로 보내 인증된 유저인지 확인
     *
     * @param accessToken
     * @return Authentication
     * @throws ExpiredJwtException
     */
    public Authentication getAuthentication(String accessToken) throws ExpiredJwtException {
        LOGGER.info("JwtProvider[getAuthentication] request data: {}", accessToken);
        Claims claims = Jwts.parserBuilder().setSigningKey(encodedKey).build().parseClaimsJws(accessToken).getBody();

        if (claims.get("role") == null) {
            LOGGER.info("JwtProvider[getAuthentication] accessToken claims role: {}", claims.get("role"));
            throw new RuntimeException("토큰에 권한정보가 없습니다.");
        }

        Collection<? extends GrantedAuthority> roles = Arrays.stream(claims.get("role").toString().split(",")).map(SimpleGrantedAuthority::new).toList();
        UserDetails user = new User(claims.getSubject(), "", roles);
        return new UsernamePasswordAuthenticationToken(user, "", roles);
    }

//    public Authentication checkRefreshToken(String refreshToken) throws ExpiredJwtException {
//        Claims claims = Jwts.parserBuilder().setSigningKey(encodedKey).build().parseClaimsJws(refreshToken).getBody();
//
//        UserDetails user = new User(claims.getSubject(), "", null);
//        return new UsernamePasswordAuthenticationToken(user, "", null);
//    }

    public boolean tokenMatches(String accessToken, String refreshToken) {
        LOGGER.info("JwtTokenProvider[tokenMatches] accessToken: {} refreshToken: {}", accessToken, refreshToken);
        Claims accessTokenClaim = Jwts.parserBuilder().setSigningKey(encodedKey).build().parseClaimsJws(accessToken).getBody();
        Claims refreshTokenClaim = Jwts.parserBuilder().setSigningKey(encodedKey).build().parseClaimsJws(refreshToken).getBody();

        if (accessTokenClaim.getSubject().equals(refreshTokenClaim.getSubject()))
            return true;

        return false;
    }

    // 인증 시 1이면(token에 role 내용도 있다는 것은 access token) access token, 0이면 refresh token을 의미
    public boolean validateToken(String accessToken, String refreshToken) {
        LOGGER.info("JwtTokenProvider[validateToken] accessToken: {} refreshToken: {}", accessToken, refreshToken);
        try {
            //access token
            LOGGER.info("JwtTokenProvider[validateToken] validate accessToken");
            Claims accessTokenClaims = Jwts.parserBuilder().setSigningKey(encodedKey).build().parseClaimsJws(accessToken).getBody();
            if (accessTokenClaims.containsKey("role")) {
                LOGGER.info("JwtTokenProvider[validateToken] accessToken");
            }
            LOGGER.info("JwtTokenProvider[validateToken] validate refreshToken");
            Claims refreshTokenClaims = Jwts.parserBuilder().setSigningKey(encodedKey).build().parseClaimsJws(refreshToken).getBody();

            LOGGER.info("JwtTokenProvider[validateToken] refreshToken");
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new RuntimeException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            LOGGER.info("JwtTokenProvider[validateToken] expired token");
            return false;
//            throw new RuntimeException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("JWT 토큰이 잘못되었습니다.");
        }
    }
//
//    // 토큰의 유효성 + 만료일자 확인
//    public boolean validateToken(String jwtToken) {
//        LOGGER.info("JwtTokenProvider[validateToken] jwtToken data: {}", jwtToken);
//        try {
//            Jws<Claims> claims = Jwts.parser().setSigningKey(accessSecret).parseClaimsJws(jwtToken);
//            logger.debug("================"+claims.getBody().get("sub").toString());
//            LOGGER.info("JwtTokenProvider[validateToken] jwtToken data: {}", jwtToken);
//            return !claims.getBody().getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public String validateRefreshToken(RefreshToken refreshTokenObj){
//
//        // refresh 객체에서 refreshToken 추출
//        String refreshToken = refreshTokenObj.getRefreshToken();
//        try {
//            // 검증
//            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecret).parseClaimsJws(refreshToken);
//            //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성합니다.
//            if (!claims.getBody().getExpiration().before(new Date())) {
//                return recreationAccessToken(claims.getBody().get("sub").toString(), claims.getBody().get("roles"));
//            }
//        }catch (Exception e) {
//
//            //refresh 토큰이 만료되었을 경우, 로그인이 필요합니다.
//            return null;
//
//        }
//
//        return null;
//    }

}
