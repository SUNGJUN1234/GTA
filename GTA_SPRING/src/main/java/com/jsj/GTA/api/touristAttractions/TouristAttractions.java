package com.jsj.GTA.api.touristAttractions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TouristAttractions {

    /**식별 코드*/
    @JsonProperty("id")
    private String id;
    /**관광지명*/
    @JsonProperty("tourDestNm")
    private String tourDestNm;
    /**관광지 구분*/
    @JsonProperty("operationRuleNm")
    private String operationRuleNm;
    /**소재지 도로명주소*/
    @JsonProperty("addrRoad")
    private String addrRoad;
    /**소재지 지번주소*/
    @JsonProperty("addrJibun")
    private String addrJibun;
    /**위치 좌표 위도*/
    @JsonProperty("lat")
    private String lat;
    /**위치 좌표 경도*/
    @JsonProperty("lng")
    private String lng;
    /**면적 m^2*/
    @JsonProperty("area")
    private String area;
    /**공공편익시설 정보*/
    @JsonProperty("publicConvFcltInfo")
    private String publicConvFcltInfo;
    /**숙박시설 정보*/
    @JsonProperty("accomInfo")
    private String accomInfo;
    /**운동 및 오락시설 정보*/
    @JsonProperty("sportsEnterFcltInfo")
    private String sportsEnterFcltInfo;
    /**휴양 및 문화시설 정보*/
    @JsonProperty("recreationalCultFcltInfo")
    private String recreationalCultFcltInfo;
    /**접객시설 정보*/
    @JsonProperty("hospitalityFcltInfo")
    private String hospitalityFcltInfo;
    /**지원시설 정보*/
    @JsonProperty("supportFcltInfo")
    private String supportFcltInfo;
    /**지정일자*/
    @JsonProperty("dsgnDate")
    private String dsgnDate;
    /**수용인원수*/
    @JsonProperty("capacity")
    private int capacity;
    /**주차가능수*/
    @JsonProperty("availParkingCnt")
    private int availParkingCnt;
    /**관광지소개*/
    @JsonProperty("tourDestIntro")
    private String tourDestIntro;
    /**관리기관전화번호*/
    @JsonProperty("mngAgcTel")
    private String mngAgcTel;
    /**관리기관*/
    @JsonProperty("mngAgcNm")
    private String mngAgcNm;
    /**데이터기준일자*/
    @JsonProperty("syncTime")
    private String syncTime;

    @Builder
    public TouristAttractions(
        String id,
        String tourDestNm,
        String operationRuleNm,
        String addrRoad,
        String addrJibun,
        String lat,
        String lng,
        String area,
        String publicConvFcltInfo,
        String accomInfo,
        String sportsEnterFcltInfo,
        String recreationalCultFcltInfo,
        String hospitalityFcltInfo,
        String supportFcltInfo,
        String dsgnDate,
        int capacity,
        int availParkingCnt,
        String tourDestIntro,
        String mngAgcTel,
        String mngAgcNm,
        String syncTime
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
        this.accomInfo = accomInfo;
        this.sportsEnterFcltInfo = sportsEnterFcltInfo;
        this.recreationalCultFcltInfo = recreationalCultFcltInfo;
        this.hospitalityFcltInfo = hospitalityFcltInfo;
        this.supportFcltInfo = supportFcltInfo;
        this.dsgnDate = dsgnDate;
        this.capacity = capacity;
        this.availParkingCnt = availParkingCnt;
        this.tourDestIntro = tourDestIntro;
        this.mngAgcTel = mngAgcTel;
        this.mngAgcNm = mngAgcNm;
        this.syncTime = syncTime;
    }
}
