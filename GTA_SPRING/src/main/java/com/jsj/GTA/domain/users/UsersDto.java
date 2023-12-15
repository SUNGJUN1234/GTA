package com.jsj.GTA.domain.users;

import com.jsj.GTA.config.auth.Platform;
import com.jsj.GTA.domain.stamps.Stamps;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class UsersDto {

    private Long id;
    private String userId;
    private String name;
    private String nickname;
    private String email;
    private String picture;
    private String platform;
    private String role;
    private Date issueDate;
    private Date expirationDate;
    private Status status;

    public UsersDto(Users entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.nickname = entity.getNickname();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
        this.platform = entity.getPicture();
        this.role = entity.getRole().getKey();
        this.issueDate = entity.getIssueDate();
        this.expirationDate = entity.getExpirationDate();
        this.status = entity.getStatus();
    }

    public Users toEntity() {
        return Users.builder()
            .userId(userId)
            .nickname(nickname)
            .email(email)
            .picture(picture)
            .platform(Platform.valueOf(platform))
            .role(Role.valueOf(role))
            .issueDate(issueDate)
            .expirationDate(expirationDate)
            .status(status)
            .build();
    }
}
