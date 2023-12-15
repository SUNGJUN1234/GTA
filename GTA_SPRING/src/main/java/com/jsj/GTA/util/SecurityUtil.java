package com.jsj.GTA.util;

import com.jsj.GTA.service.TouristAttractionsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NoArgsConstructor
public class SecurityUtil {

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";
    private static final Logger LOGGER = LoggerFactory.getLogger(TouristAttractionsService.class);

    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            LOGGER.info("SecurityUtil[getCurrentUsername] Authentication data doesn't exist");
            throw new RuntimeException("인증 정보가 없습니다.");
        }
        LOGGER.info("SecurityUtil[getCurrentUsername] Authentication data: {}", authentication);
        return authentication.getName();
    }

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        LOGGER.info("SecurityUtil[getCookie] name: {}", name);
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    LOGGER.info("SecurityUtil[getCookie] value: {}", cookie.getValue());
                    return Optional.of(cookie);
                }
            }
        }
        LOGGER.info("SecurityUtil[getCookie] cookie doesn't exist");
        return Optional.empty();
    }

    public static String getAccessToken(HttpServletRequest request) {
        LOGGER.info("SecurityUtil[getAccessToken] request access token");
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        if (headerValue == null) {
            LOGGER.info("SecurityUtil[getAccessToken] access token doesn't exist");
            return null;
        }

        if (headerValue.startsWith(TOKEN_PREFIX)) {
            String token = headerValue.substring(TOKEN_PREFIX.length());
            LOGGER.info("SecurityUtil[getAccessToken] access token data: {}", token);
            return token;
        }

        return null;
    }
}
