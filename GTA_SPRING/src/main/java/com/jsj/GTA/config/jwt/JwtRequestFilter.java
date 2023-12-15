package com.jsj.GTA.config.jwt;

import com.jsj.GTA.api.touristAttractions.TouristAttractionsService;
import com.jsj.GTA.config.auth.SecurityUtil;
import com.jsj.GTA.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    private final Logger LOGGER = LoggerFactory.getLogger(TouristAttractionsService.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = SecurityUtil.getAccessToken(request);
        Optional<Cookie> cookies = SecurityUtil.getCookie(request, "refresh_token");
        Cookie cookie = null;
        String refreshToken = null;
        if (cookies.isPresent()) {
            cookie = cookies.get();
            refreshToken = cookie.getValue();
        }
        LOGGER.info("JwtRequestFilter[doFilterInternal] Bearer Token: {} refresh_token: {}", accessToken, refreshToken);
        if (accessToken != null && refreshToken != null) {
            if (!jwtTokenProvider.validateToken(accessToken, refreshToken)) {
                LOGGER.info("JwtRequestFilter[doFilterInternal] expired token");
                TokenDto tokenDto = tokenService.regenerateToken(refreshToken);
                accessToken = tokenDto.getAccessToken();
                refreshToken = tokenDto.getRefreshToken();
                LOGGER.info("JwtRequestFilter[doFilterInternal]regenerateToken Bearer Token: {} refresh_token: {}", accessToken, refreshToken);
            }
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
