package com.jsj.GTA.api.touristAttractions.redis;

import com.jsj.GTA.api.touristAttractions.TouristAttractionsResponseDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.List;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "touristAttractionsWithImageUrl")
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class TouristAttractionsResponseRedisDto {
    @Id
    private String id;
    private String tourDestNm;
    private String operationRuleNm;
    private String addrRoad;
    private String addrJibun;
    @Indexed
    private double lat;
    @Indexed
    private double lng;
    private String area;
    private String publicConvFcltInfo;
    private String tourDestIntro;
    private String mngAgcTel;
    private String mngAgcNm;
    private List<String> images;

    public static TouristAttractionsResponseRedisDto createDto(TouristAttractionsResponseDto entity) {
        TouristAttractionsResponseRedisDto dto = new TouristAttractionsResponseRedisDto(
                entity.getId(),
                entity.getTourDestNm(),
                entity.getOperationRuleNm(),
                entity.getAddrRoad(),
                entity.getAddrJibun(),
                Double.parseDouble(entity.getLat()), // redis 지리공간 메서드 확장성을 위해 double 로 저장
                Double.parseDouble(entity.getLng()),
                entity.getArea(),
                entity.getPublicConvFcltInfo(),
                entity.getTourDestIntro(),
                entity.getMngAgcTel(),
                entity.getMngAgcNm(),
                entity.getImages());
        return dto;
    }
}
