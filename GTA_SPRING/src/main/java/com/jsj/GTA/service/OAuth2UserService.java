package com.jsj.GTA.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsj.GTA.domain.oauth.OAuthAttributes;
import com.jsj.GTA.domain.users.Users;
import com.jsj.GTA.domain.users.UsersDto;
import com.jsj.GTA.domain.users.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class OAuth2UserService {

    private final UsersRepository usersRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(TouristAttractionsService.class);

    @Transactional
    public Map<String, Object> findOrSaveUsers(String id_token, String platform) throws ParseException, JsonProcessingException {
        LOGGER.info("OAuth2UserService[findOrSaveUsers] platform: {} token: {}", platform, id_token);
        OAuthAttributes oAuthAttributes;
        switch (platform) {
            case "kakao":
                oAuthAttributes = getKakaoData(id_token);
                LOGGER.info("OAuth2UserService[findOrSaveUsers] kakao data: {}", oAuthAttributes.getEmail());
                break;
            default:
                throw new RuntimeException("제공하지 않는 인증기관입니다.");
        }

        // OAuth2 로그인 시 키 값이 된다.
        // 구글은 키 값이 "sub"이고, 네이버는 "response"이고, 카카오는 "id"이다.
        // 각각 다르므로 이렇게 따로 변수로 받아서 넣어줘야함.

        Integer httpStatus = HttpStatus.OK.value();

        OAuthAttributes finalOAuthAttributes = oAuthAttributes;
        LOGGER.info("OAuth2UserService[findOrSaveUsers] finalOAuthAttributes data: {}", finalOAuthAttributes.getEmail());
        Users users = usersRepository.findByEmail(oAuthAttributes.getEmail())
                .orElseGet(() -> {
                    Users newUser = finalOAuthAttributes.toEntity();
                    LOGGER.info("OAuth2UserService[findOrSaveUsers] user: {}", newUser.getEmail());
//                    newUser.updateRole(Role.ROLE_USER);
                    return usersRepository.save(newUser);
                });

        if (users.getNickname() == null) {
            httpStatus = HttpStatus.CREATED.value();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("dto", new UsersDto(usersRepository.findById(users.getId()).get()));
        result.put("status", httpStatus);

        return result;
    }

    private OAuthAttributes getKakaoData(String accessToken) throws JsonProcessingException {
        String url = "https://kapi.kakao.com/v2/user/me";
        String result = Call("GET", url, "Bearer " + accessToken, null);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(result, OAuthAttributes.class);
    }

    public String Call(String method, String reqURL, String header, String param) {
        String result = "";
        try {
            String response = "";
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Authorization", header);
            if (param != null) {
                System.out.println("param : " + param);
                conn.setDoOutput(true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                bw.write(param);
                bw.flush();

            }
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            System.out.println("reqURL : " + reqURL);
            System.out.println("method : " + method);
            System.out.println("Authorization : " + header);
            InputStream stream = conn.getErrorStream();
            if (stream != null) {
                try (Scanner scanner = new Scanner(stream)) {
                    scanner.useDelimiter("\\Z");
                    response = scanner.next();
                }
                System.out.println("error response : " + response);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            br.close();
        } catch (IOException e) {
            return e.getMessage();
        }

        return result;
    }

}