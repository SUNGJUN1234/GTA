package com.jsj.GTA.config.auth.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jsj.GTA.domain.users.Role;
import com.jsj.GTA.domain.users.Status;
import com.jsj.GTA.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@NoArgsConstructor
public class OAuthAttributes {

    private String id;
    private Date connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;


    @Getter
    public static class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;

    }

    @Getter
    public static class KakaoAccount {
        private boolean profile_nickname_needs_agreement;
        private boolean profile_image_needs_agreement;
        private Profile profile; // 수정된 부분
        private boolean has_email;
        private boolean email_needs_agreement;
        @JsonProperty("is_email_valid")
        private boolean is_email_valid;
        @JsonProperty("is_email_verified")
        private boolean is_email_verified;
        private String email;

        @Getter
        public static class Profile {
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
            @JsonProperty("is_default_image")
            private boolean is_default_image;
        }
    }

    public String getEmail() {
        return this.kakao_account.email;
    }
    public String getNickname() {
        return this.properties.nickname;
    }
    public String getProfile_image() {
        return this.properties.profile_image;
    }

    /**
     * 처음 가입할 때, OAuthAttributes 에서 User 엔터티 생성
     */
    public Users toEntity() {
        return Users.builder()
                .issueDate(connected_at)
                .userId(id)
                .nickname(properties.nickname)
                .email(kakao_account.email)
                .picture(properties.profile_image)
                .role(Role.USER)
                .status(Status.NORMAL)
                .build();
    }
}
