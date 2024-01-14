package com.jsj.GTA.service;

import com.jsj.GTA.domain.users.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public void authenticateLogin(LoginRequestDto dto) {
        LOGGER.info("[authenticateLogin] requestDTO data : {}", dto);
        UsernamePasswordAuthenticationToken authenticationToken = dto.toAuthentication();
        LOGGER.info("[authenticateLogin] authenticationToken data : {}", authenticationToken);
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }
}
