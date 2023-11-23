package com.jsj.GTA.domain.touristAttractions;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "지리공간", description = "db에 저장된 지리공간 정보")
@Entity // 테이블과 링크될 클래스임을 나타냄(기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭함 - TableName.java -> table_name table)
@Getter
@NoArgsConstructor
public class Geospatial {
    @Schema(description = "지리공간 id")
    @Id // 해당 테이블의 PK 필드를 설정함
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙 설정
    private Long id;
    @Schema(description = "위치 좌표 경도")
    @Column(nullable = false)
    private double lng;
    @Schema(description = "위치 좌표 위도")
    @Column(nullable = false)
    private double lat;
    @Schema(description = "관광지 id")
    @Column(nullable = false)
    private String touristAttractionsId;

    @Builder
    public Geospatial(double lng, double lat, String touristAttractionsId) {
        this.lng = lng;
        this.lat = lat;
        this.touristAttractionsId = touristAttractionsId;
    }
}
