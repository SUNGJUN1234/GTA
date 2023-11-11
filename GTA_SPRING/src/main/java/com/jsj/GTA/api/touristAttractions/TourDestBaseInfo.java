package com.jsj.GTA.api.touristAttractions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 공공 데이터를 담는 객체
 */
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
