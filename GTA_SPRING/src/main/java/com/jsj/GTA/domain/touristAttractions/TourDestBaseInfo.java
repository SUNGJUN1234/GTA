package com.jsj.GTA.domain.touristAttractions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 공공 데이터를 담는 객체
 */
@Schema(name = "api 관광지", description = "공공 데이터에서 받은 관광지 정보")
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourDestBaseInfo {
    private List<TouristAttractions> tourDestBaseInfo;

    @JsonCreator
    public TourDestBaseInfo(@JsonProperty("TourDestBaseInfo") List<TouristAttractions> tourDestBaseInfo) {
        this.tourDestBaseInfo = tourDestBaseInfo;
    }
}
