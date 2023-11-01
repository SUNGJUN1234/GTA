package com.jsj.GTA.config.auth.dto;


// 직렬화 : 자바 시스템 내부에서 사용되는 Object 또는 Data 를 외부의 자바 시스템에서도 사용할 수 있도록 byte 형태로 변환하는 것

import com.jsj.GTA.domain.users.Users;
import lombok.Getter;

import java.io.Serializable;

/**인증된 사용자 정보를 저장하기 위한, 직렬화 기능을 가진 dto*/
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Users users) {
        this.name = users.getName();
        this.email = users.getEmail();
        this.picture = users.getPicture();
    }
}
