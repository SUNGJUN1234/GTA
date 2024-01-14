package com.jsj.GTA.config;

import com.jsj.GTA.service.TouristAttractionsService;
import com.jsj.GTA.config.jwt.JwtAccessDeniedHandler;
import com.jsj.GTA.config.jwt.JwtAuthenticationEntryPoint;
import com.jsj.GTA.config.jwt.JwtRequestFilter;
import com.jsj.GTA.config.jwt.JwtTokenProvider;
import com.jsj.GTA.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // Spring Security 설정을 활성화
public class SecurityConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private static final String[] URL_TO_PERMIT = {
            "/member/login",
            "/member/signup",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/auth/**"
    };
    private static final String[] STATIC_RESOURCES = {
            "/css/**",
            "/images/**",
            "/js/**",
            "/profile/**"
    };

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 람다식으로 변환
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // csrf 설정 끄기

                .sessionManagement((session) -> session // 세션 STATELESS
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(exceptionHandling -> exceptionHandling // 예외 처리
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))

                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                .securityMatcher("/api/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(STATIC_RESOURCES).permitAll() // 정적 리소스에 대해 접근 허용
                        .requestMatchers(URL_TO_PERMIT).permitAll()
                        .anyRequest().authenticated()) // 위의 규칙과 일치하지 않는 기타 요청에는 인증이 필요합니다.

                .logout((logout) -> logout
                        .logoutSuccessUrl("/"))

                .addFilterBefore( // jwt 필터를 usernamepassword 인증 전에 실행
                        new JwtRequestFilter(jwtTokenProvider, tokenService), UsernamePasswordAuthenticationFilter.class);
        LOGGER.info("securityConfig");
        return http.build();
    }

    @Bean
    public CorsFilter corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
