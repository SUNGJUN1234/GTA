package com.jsj.GTA.controller;

import com.jsj.GTA.service.TouristAttractionsService;
import com.jsj.GTA.util.SecurityUtil;
import com.jsj.GTA.domain.jwt.TokenDto;
import com.jsj.GTA.domain.users.LoginRequestDto;
import com.jsj.GTA.domain.users.UsersDto;
import com.jsj.GTA.domain.users.UsersRequestDto;
import com.jsj.GTA.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@Tag(name = "사용자 관련", description = "사용자 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class UsersController {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    private final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<String> memberSignup(@RequestBody UsersRequestDto requestDto) {
        LOGGER.info("[memberSignup] memberSignup data : {}", requestDto);
        System.out.println(requestDto.getEmail());
        String rawPassword = requestDto.getPassword();
        if (rawPassword != null) {
            requestDto.updatePassword(passwordEncoder.encode(rawPassword));
            System.out.println(requestDto.getPassword());
            usersService.signup(requestDto);
            return new ResponseEntity<>("", HttpStatus.OK);
        } else {
            // Handle the case where the password is null (throw an exception or return an error response)
            return new ResponseEntity<>("Password cannot be null", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<String> memberLogin(@RequestBody LoginRequestDto loginRequestDto) {
        LOGGER.info("[memberLogin] loginRequestDto data userId: {}", loginRequestDto.getUserId());
        TokenDto tokenDto = usersService.login(loginRequestDto);
        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", tokenDto.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(tokenDto.getDuration())
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header("Set-Cookie", responseCookie.toString())
                .header("Authorization", "Bearer " + tokenDto.getAccessToken()).build();
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<String> memberLogout() {
        String username = SecurityUtil.getCurrentUsername();
        LOGGER.info("[memberLogout] username: {}", username);
        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header("Set-Cookie", responseCookie.toString())
                .header("Authorization", "").build();
    }

    @Operation(summary = "회원정보 호출")
    @GetMapping("/getUsersData")
    public ResponseEntity<UsersDto> loadUsersData() {
        return ResponseEntity.ok(usersService.findByUserId(SecurityUtil.getCurrentUsername()));
    }

    @Operation(summary = "회원탈퇴")
    @PostMapping("/withdraw")
    public ResponseEntity<String> memberWithdraw(@RequestBody LoginRequestDto loginRequestDto) {
        String userId = loginRequestDto.getUserId();
        LOGGER.info("[memberWithdraw] loginRequestDto data userId: {}", loginRequestDto.getUserId());
        usersService.withdraw(loginRequestDto);

        return ResponseEntity.ok(userId);
    }
}