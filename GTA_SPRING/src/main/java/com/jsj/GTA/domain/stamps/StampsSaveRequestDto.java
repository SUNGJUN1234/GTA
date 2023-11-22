package com.jsj.GTA.domain.stamps;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Schema(description = "db에 저장하기 위해, 클라이언트로부터 받은 스탬프 정보")
@Getter
@NoArgsConstructor
public class StampsSaveRequestDto {

    // Entity 클래스가 변경되면 여러 클래스에 영향을 끼치기 때문에
    // Controller 에서 쓸 Dto 분리
    @Schema(description = "관광지 id")
    private String touristAttractionsId;
    @Schema(description = "사용자 id")
    private Long usersId;
    @Schema(description = "스탬프 이름")
    private String name;
    @Schema(description = "스탬프 발급일 - null")
    private String  issueDate;
    @Schema(description = "만료일")
    private String  expirationDate;

    @Builder
    public StampsSaveRequestDto(String touristAttractionsId, Long usersId, String name, String issueDate, String expirationDate) {
        this.touristAttractionsId = touristAttractionsId;
        this.usersId = usersId;
        this.name = name;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
    }

    public Stamps toEntity() {
        return Stamps.builder()
                .touristAttractionsId(touristAttractionsId)
                .usersId(usersId)
                .name(name)
                .issueDate(new Date().toString())
                .build();
    }
}
