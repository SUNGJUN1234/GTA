package com.jsj.GTA.domain.stamps;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "사용자가 보유한 스탬프 개수")
@Getter
public class UsersStampCountDto {
    @Schema(description = "사용자 id")
    private Long id;
    @Schema(description = "사용자가 보유한 스탬프 개수")
    private Long count;

    public UsersStampCountDto(Long id, Long count) {
        this.id = id;
        this.count = count;
    }
}
