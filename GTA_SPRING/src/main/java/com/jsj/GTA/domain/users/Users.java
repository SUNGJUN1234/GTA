package com.jsj.GTA.domain.users;

import com.jsj.GTA.domain.oauth.Platform;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;
    private String password;
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;
    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    private Platform platform; // kakao, naver, google
    // JPA 로 데이터베이스에 값을 저장할 때, Enum 값을 어떤 형태로 저장할지를 결정함
    // (기본적으로 int 이나 숫자가 의미하는 값을 알 수 없기 때문에 이를 문자열로 저장할 수 있도록 선언)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private Date issueDate;

    @Column
    private Date expirationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Users(String userId, String password, String nickname, String email, String picture, Platform platform, Role role, Date issueDate, Date expirationDate, Status status) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
        this.platform = platform;
        this.role = role;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.status = status;
    }

    public Users update(String nickname, String picture) {
        this.nickname = nickname;
        this.picture=picture;
        return this;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void delete() {
        this.status = Status.DELETE;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }


}
