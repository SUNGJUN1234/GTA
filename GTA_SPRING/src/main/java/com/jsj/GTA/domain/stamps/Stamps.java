package com.jsj.GTA.domain.stamps;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity // 테이블과 링크될 클래스임을 나타냄(기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭함 - TableName.java -> table_name table)
@Getter
@NoArgsConstructor
public class Stamps {

    @Id // 해당 테이블의 PK 필드를 설정함
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙 설정
    private Long id;

    @Column(nullable = false)
    private String touristAttractionsId;
    @Column(nullable = false)
    private Long usersId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String issueDate;
    private String expirationDate;

    @Builder // Setter 대신, Builder 를 통해 생성자 값 채운 뒤 DB 에 삽입, 어느 필드에 어떤 값을 채울지 명확히 인지 가능하다는 장점.
    public Stamps(String touristAttractionsId, Long usersId, String name, String issueDate) {
        this.touristAttractionsId = touristAttractionsId;
        this.usersId = usersId;
        this.name = name;
        this.issueDate = issueDate;
    }
}
