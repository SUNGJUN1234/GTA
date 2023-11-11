package com.jsj.GTA.config.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @GetMapping("/loginInfo")
    public Map<String, Object> oauthLoginInfo(Authentication authentication){
	if(!authentication.isAuthenticated()) {
            return null;
        }    
	//oAuth2User.toString() 예시 : Name: [2346930276], Granted Authorities: [[USER]], User Attributes: [{id=234693027, provider=kakao, name=이름, email=이메일@naver.com}]
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        //attributes.toString() 예시 : {id=234693027, provider=kakao, name=이름, email=이메일@naver.com}
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return attributes;
    }
}
