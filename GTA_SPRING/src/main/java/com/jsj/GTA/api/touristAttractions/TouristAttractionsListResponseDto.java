package com.jsj.GTA.api.touristAttractions;

import com.jsj.GTA.util.Crawler;
import lombok.Getter;

import java.io.IOException;
import java.util.List;

@Getter
public class TouristAttractionsListResponseDto {
    private String id;
    private String tourDestNm;
    private String operationRuleNm;
    private String addrRoad;
    private String addrJibun;
    private String lat;
    private String lng;
    private String area;
    private String publicConvFcltInfo;
    private String tourDestIntro;
    private String mngAgcTel;
    private String mngAgcNm;
    private List<String> images;

    public TouristAttractionsListResponseDto(TouristAttractions entity){
        this.id = entity.getId();
        this.tourDestNm = entity.getTourDestNm();
        this.addrRoad = entity.getAddrRoad();
        this.addrJibun = entity.getAddrJibun();
        this.lat = entity.getLat();
        this.lng = entity.getLng();
        this.area = entity.getArea();
        this.publicConvFcltInfo = entity.getPublicConvFcltInfo();
        this.tourDestIntro = entity.getTourDestIntro();
        this.mngAgcTel = entity.getMngAgcTel();
        this.mngAgcNm = entity.getMngAgcNm();
        try {
            this.images = Crawler.getImages(entity.getTourDestNm());
        } catch (Exception e) {
        }
    }
}