package com.jsj.GTA.api.touristAttractions.redis;

import lombok.Getter;
import org.springframework.data.geo.Distance;

@Getter
public class TouristAttractionsGeoResponseDto {
    private TouristAttractionsResponseRedisDto touristAttractionsResponseRedisDto;
    private Distance distance;

    public TouristAttractionsGeoResponseDto(TouristAttractionsResponseRedisDto entity, Distance distance) {
        this.touristAttractionsResponseRedisDto = entity;
        this.distance = distance;
    }
}
