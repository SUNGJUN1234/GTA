package com.jsj.GTA.domain.touristAttractions;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "관광지 이미지", description = "db에 저장된 관광지 이미지 정보")
@Entity
@Getter
@NoArgsConstructor
public class TouristAttractionsImageUrl {
    @Schema(description = "이미지 id")
    @Id // 해당 테이블의 PK 필드를 설정함
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙 설정
    private Long id;
    @Schema(description = "관광지 id")
    @Column(nullable = false)
    private String touristAttractionsId;
    @Schema(description = "관광지 이미지")
    @Column(nullable = false)
    private String image;

    @Builder // Setter 대신, Builder 를 통해 생성자 값 채운 뒤 DB 에 삽입, 어느 필드에 어떤 값을 채울지 명확히 인지 가능하다는 장점.
    public TouristAttractionsImageUrl(String touristAttractionsId, String image) {
        this.touristAttractionsId = touristAttractionsId;
        this.image = image;
    }
}
