package com.jsj.GTA.api.touristAttractions.redis;

import com.jsj.GTA.api.touristAttractions.TouristAttractionsResponseDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "touristAttractionsWithImageUrl")
public class TouristAttractionsResponseRedisDto {
    @Id
    private String id;
    private String tourDestNm;
    private String operationRuleNm;
    private String addrRoad;
    private String addrJibun;
    @Indexed
    private String lat;
    @Indexed
    private String lng;
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
                entity.getLat(),
                entity.getLng(),
                entity.getArea(),
                entity.getPublicConvFcltInfo(),
                entity.getTourDestIntro(),
                entity.getMngAgcTel(),
                entity.getMngAgcNm(),
                entity.getImages());
        return dto;
    }
}
