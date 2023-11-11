package com.jsj.GTA.controller;

import com.jsj.GTA.config.auth.LoginUser;
import com.jsj.GTA.config.auth.dto.SessionUser;
//import com.jsj.GTA.domain.MainResponseDto;
import com.jsj.GTA.domain.stamps.StampsListResponseDto;
import com.jsj.GTA.service.StampsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class MainController {

    @GetMapping("/")
    public String main(@LoginUser SessionUser users, HttpSession session) {
        if (users != null) {
            session.setAttribute("users", users); // 세션에 사용자 정보를 저장
//	    return Map.of(
//                    "name", users.getName(),
//                    "email", users.getEmail(),
//                    "picture",users.getPicture()
//            );
//        } else {
//            return null;
        }
        return "index";
    }
}
