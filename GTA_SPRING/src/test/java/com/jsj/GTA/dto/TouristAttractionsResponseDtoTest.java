package com.jsj.GTA.dto;

import com.jsj.GTA.config.api.touristAttractions.TouristAttractions;
import com.jsj.GTA.config.api.touristAttractions.TouristAttractionsResponseDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TouristAttractionsResponseDtoTest {

    @Test
    public void 관광지_불러오기_Lombok() throws Exception {
        //given
        String id = "1";
        String tourDestNm = "광주광역시공원&광주광역시향교";
        String addrRoad = "광주광역시 남구 중앙로 107번길 15";
        String addrJibun = "";
        String lat = "35.14707323";
        String lng = "126.9100957";
        String area = "28800";
        String publicConvFcltInfo = "화장실+주차장";
        String tourDestIntro = "선비들의 정신이 살아 있음을 느낄 수 있음";
        String mngAgcTel = "062-672-0660";
        String mngAgcNm = "광주광역시공원관리사무소";
        //when
        TouristAttractionsResponseDto dto = new TouristAttractionsResponseDto(new TouristAttractions(
                id,
                tourDestNm,
                "",
                addrRoad,
                addrJibun,
                lat,
                lng,
                area,
                publicConvFcltInfo,
                "",
                "",
                "",
                "",
                "",
                "",
                0,
                0,
                tourDestIntro,
                mngAgcTel,
                mngAgcNm,
                ""));
        //then
        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getTourDestNm()).isEqualTo(tourDestNm);
        assertThat(dto.getAddrRoad()).isEqualTo(addrRoad);
        assertThat(dto.getAddrJibun()).isEqualTo(addrJibun);
        assertThat(dto.getLat()).isEqualTo(lat);
        assertThat(dto.getLng()).isEqualTo(lng);
        assertThat(dto.getArea()).isEqualTo(area);
        assertThat(dto.getPublicConvFcltInfo()).isEqualTo(publicConvFcltInfo);
        assertThat(dto.getTourDestIntro()).isEqualTo(tourDestIntro);
        assertThat(dto.getMngAgcTel()).isEqualTo(mngAgcTel);
        assertThat(dto.getMngAgcNm()).isEqualTo(mngAgcNm);
    }
}
