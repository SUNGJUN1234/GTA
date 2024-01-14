package com.jsj.GTA.service;

import com.jsj.GTA.domain.jwt.TokenDto;
import com.jsj.GTA.domain.users.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final TokenService tokenService;
    private final AuthService authService;

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        LOGGER.info("[loadUserByUsername] request data: {}", userId);
        return usersRepository.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("userId: " + userId + "를 데이터베이스에서 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Users users) {
        LOGGER.info("[createUserDetails] request data: {}", users.toString());
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(users.getRole().getKey());

        return new User(
                users.getUserId(),
                users.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }

    public UsersDto findMemberByEmail(String email) {
        Users entity = usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 email을 가진 사용자가 존재하지 않습니다."));
        return new UsersDto(entity);
    }

    public UsersDto findByUserId(String userId) {
        LOGGER.info("[findByUserId] request data: {}", userId);
        Users entity = usersRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("해당 id를 가진 사용자가 존재하지 않습니다."));
        return new UsersDto(entity);
    }

    @Transactional
    public Long saveUsers(UsersDto requestDto) {
        return usersRepository.save(requestDto.toEntity()).getId();
    }

    /**
     * UsernamePasswordAuthenticationToken을 통한 Spring Security인증 진행
     * 이후 tokenService에 userId값을 전달하여 토큰 생성
     * @param requestDto
     * @return TokenDTO
     */
    @Transactional
    public TokenDto login(LoginRequestDto requestDto) {
        LOGGER.info("[login] requestDto data: {}", requestDto.toString());
        authService.authenticateLogin(requestDto);

        Users users = usersRepository.findByUserId(requestDto.getUserId()).get();
        LOGGER.info("[login] findByUserId data userId: {}", users.getUserId());
        TokenDto tokenDto = tokenService.createToken(users);
        LOGGER.info("[login] tokenDto TokenType: {} AccessToken: {} RefreshToken: {} Duration: {}",
                tokenDto.getTokenType(),
                tokenDto.getAccessToken(),
                tokenDto.getRefreshToken(),
                tokenDto.getDuration());
        return tokenDto;
    }

    @Transactional
    public void signup(UsersRequestDto requestDto) {
        LOGGER.info("[signup] requestDTO data: {}", requestDto.toString());
        Optional<Users> entity = usersRepository.findByUserId(requestDto.getUserId());
        if(entity.isPresent()) {
            LOGGER.info("[signup] already exist data, entity: {}", entity.get().getUserId());
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        } else {
            Users users = requestDto.toEntity(requestDto);
            LOGGER.info("[signup] new users data: {}", users.getUserId());
            usersRepository.save(users);
        }
//        users.updateRole(Role.USER);
//        usersRepository.save(users);
    }

    @Transactional
    public void withdraw(LoginRequestDto requestDto) {
        LOGGER.info("[withdraw] requestDto data: {}", requestDto.toString());
        authService.authenticateLogin(requestDto);

        Optional<Users> users = usersRepository.findByUserId(requestDto.getUserId());
        LOGGER.info("[withdraw] findByUserId data userId: {}", users.get().getUserId());
        if(users.isPresent()) {
            Users user = users.get();
            user.delete();
            LOGGER.info("[withdraw] users status: {}", user.getStatus());
            usersRepository.save(user);
        }
    }
}