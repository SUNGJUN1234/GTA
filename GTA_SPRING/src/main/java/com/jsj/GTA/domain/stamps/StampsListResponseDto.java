package com.jsj.GTA.domain.stamps;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Date;

@Schema(description = "db에 저장된 다수의 스탬프 정보를 리스트 형태로 반환하기 위한 가공을 한 형태")
@Getter
public class StampsListResponseDto {
    @Schema(description = "스탬프 id")
    private Long id;
    @Schema(description = "관광지 id")
    private String touristAttractionsId;
    @Schema(description = "사용자 id")
    private Long usersId;
    @Schema(description = "스탬프 이름")
    private String name;
    @Schema(description = "스탬프 발급일")
    private String issueDate;
    @Schema(description = "스탬프 만료일")
    private String expirationDate;

    public StampsListResponseDto(Stamps entity) {
        this.id = entity.getId();
        this.touristAttractionsId = entity.getTouristAttractionsId();
        this.usersId = entity.getUsersId();
        this.name = entity.getName();
        this.issueDate = entity.getIssueDate();
        this.expirationDate = entity.getExpirationDate();
    }
}
