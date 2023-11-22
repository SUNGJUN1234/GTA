package com.jsj.GTA.api.touristAttractions.redis;

import com.jsj.GTA.api.touristAttractions.TouristAttractionsResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.List;

@Schema(description = "이미지url을 가지고 있는 캐시 스토리지 형태의 관광지")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "touristAttractionsWithImageUrl")
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class TouristAttractionsResponseRedisDto {
    @Schema(description = "관광지 id")
    @Id
    private String id;
    @Schema(description = "관광지명")
    private String tourDestNm;
    @Schema(description = "관광지 구분")
    private String operationRuleNm;
    @Schema(description = "소재지 도로명주소")
    private String addrRoad;
    @Schema(description = "소재지 지번주소")
    private String addrJibun;
    @Schema(description = "위치 좌표 위도")
    @Indexed
    private double lat;
    @Schema(description = "위치 좌표 경도")
    @Indexed
    private double lng;
    @Schema(description = "면적 m^2")
    private String area;
    @Schema(description = "공공편익시설 정보")
    private String publicConvFcltInfo;
    @Schema(description = "관광지소개")
    private String tourDestIntro;
    @Schema(description = "관리기관전화번호")
    private String mngAgcTel;
    @Schema(description = "관리기관")
    private String mngAgcNm;
    @Schema(description = "이미지url 리스트")
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
