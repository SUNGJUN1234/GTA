package com.jsj.GTA.domain.users;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

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
    public Users(Date issueDate, String name, String email, String picture, Role role, Status status) {
        this.issueDate = issueDate;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.status = status;
    }

    public Users update(String name, String picture) {
        this.name = name;
        this.picture=picture;
        return this;
    }

    public void delete() {
        this.status = Status.DELETE;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
