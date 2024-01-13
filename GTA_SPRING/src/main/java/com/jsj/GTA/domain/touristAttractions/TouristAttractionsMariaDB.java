package com.jsj.GTA.domain.touristAttractions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jsj.GTA.domain.touristAttractions.redis.TouristAttractionsResponseRedisDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "관광지", description = "db에 저장된 관광지 정보")
@Entity
@Getter
@NoArgsConstructor
public class TouristAttractionsMariaDB {

    /**식별 코드*/
    @Schema(description = "관광지 id")
    @Id
    @JsonProperty("id")
    private String id;
    /**관광지명*/
    @Schema(description = "관광지명")
    @Column(nullable = false)
    @JsonProperty("tourDestNm")
    private String tourDestNm;
    /**관광지 구분*/
    @Schema(description = "관광지 구분")
    @JsonProperty("operationRuleNm")
    private String operationRuleNm;
    /**소재지 도로명주소*/
    @Schema(description = "소재지 도로명주소")
    @JsonProperty("addrRoad")
    private String addrRoad;
    /**소재지 지번주소*/
    @Schema(description = "소재지 지번주소")
    @JsonProperty("addrJibun")
    private String addrJibun;
    /**위치 좌표 위도*/
    @Schema(description = "위치 좌표 위도")
    @JsonProperty("lat")
    private String lat;
    /**위치 좌표 경도*/
    @Schema(description = "위치 좌표 경도")
    @JsonProperty("lng")
    private String lng;
    /**면적 m^2*/
    @Schema(description = "면적 m^2")
    @JsonProperty("area")
    private String area;
    /**공공편익시설 정보*/
    @Schema(description = "공공편익시설 정보")
    @JsonProperty("publicConvFcltInfo")
    private String publicConvFcltInfo;
    /**관광지소개*/
    @Schema(description = "관광지소개")
    @JsonProperty("tourDestIntro")
    private String tourDestIntro;
    /**관리기관전화번호*/
    @Schema(description = "관리기관전화번호")
    @JsonProperty("mngAgcTel")
    private String mngAgcTel;
    /**관리기관*/
    @Schema(description = "관리기관")
    @JsonProperty("mngAgcNm")
    private String mngAgcNm;

    @Builder
    public TouristAttractionsMariaDB(
        String id,
        String tourDestNm,
        String operationRuleNm,
        String addrRoad,
        String addrJibun,
        String lat,
        String lng,
        String area,
        String publicConvFcltInfo,
        String tourDestIntro,
        String mngAgcTel,
        String mngAgcNm
    ) {
        this.id = id;
        this.tourDestNm = tourDestNm;
        this.operationRuleNm = operationRuleNm;
        this.addrRoad = addrRoad;
        this.addrJibun = addrJibun;
        this.lat = lat;
        this.lng = lng;
        this.area = area;
        this.publicConvFcltInfo = publicConvFcltInfo;
        this.tourDestIntro = tourDestIntro;
        this.mngAgcTel = mngAgcTel;
        this.mngAgcNm = mngAgcNm;
    }

    public static TouristAttractionsMariaDB apiToEntity(TouristAttractionsResponseDto entity) {
        TouristAttractionsMariaDB dto = new TouristAttractionsMariaDB(
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
                entity.getMngAgcNm());
        return dto;
    }

    public static TouristAttractionsMariaDB redisToEntity(TouristAttractionsResponseRedisDto redisDto) {
        TouristAttractionsMariaDB dto = new TouristAttractionsMariaDB(
                redisDto.getId(),
                redisDto.getTourDestNm(),
                redisDto.getOperationRuleNm(),
                redisDto.getAddrRoad(),
                redisDto.getAddrJibun(),
                String.valueOf(redisDto.getLat()),
                String.valueOf(redisDto.getLng()),
                redisDto.getArea(),
                redisDto.getPublicConvFcltInfo(),
                redisDto.getTourDestIntro(),
                redisDto.getMngAgcTel(),
                redisDto.getMngAgcNm());
        return dto;
    }
}
