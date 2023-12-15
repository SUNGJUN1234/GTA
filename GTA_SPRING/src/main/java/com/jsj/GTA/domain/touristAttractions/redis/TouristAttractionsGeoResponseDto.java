package com.jsj.GTA.domain.touristAttractions.redis;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.geo.Distance;


@Schema(description = "redis에서 지리공간 정보를 가지고 있는 관광지")
@Getter
public class TouristAttractionsGeoResponseDto {
    private TouristAttractionsResponseRedisDto touristAttractionsResponseRedisDto;
    @Schema(name = "거리")
    private Distance distance;

    public TouristAttractionsGeoResponseDto(TouristAttractionsResponseRedisDto entity, Distance distance) {
        this.touristAttractionsResponseRedisDto = entity;
        this.distance = distance;
    }
}
