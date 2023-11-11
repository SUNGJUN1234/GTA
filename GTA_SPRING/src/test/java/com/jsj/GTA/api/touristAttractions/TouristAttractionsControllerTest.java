package com.jsj.GTA.api.touristAttractions;
//package com.jsj.GTA.config.api.touristAttractions;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class TouristAttractionsControllerTest {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired TouristAttractionsService touristAttractionsService;
//    private MockMvc mvc;
//
//    @BeforeEach
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
//
//    @Test
//    void findById() throws Exception {
//        TouristAttractionsResponseDto responseDto = touristAttractionsResponseDto1;
//        mvc.perform(MockMvcRequestBuilders.get("/api/v1/touristAttractions/touristAttractions/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(toJson(responseDto)));
//    }
//
//    @Test
//    void findAllDesc() {
//    }
//
//    @Test
//    void findByStampsIdDesc() {
//    }
//
//    @Test
//    void findByUserIdDesc() {
//    }
//
//    @Test
//    void findByCoordinate() {
//    }
//
//    @Test
//    void findByNearCoordinate() {
//    }
//
//    String id = "1";
//    String tourDestNm = "광주광역시공원&광주광역시향교";
//    String addrRoad = "광주광역시 남구 중앙로 107번길 15";
//    String addrJibun = "";
//    String lat = "35.14707323";
//    String lng = "126.9100957";
//    String area = "28800";
//    String publicConvFcltInfo = "화장실+주차장";
//    String tourDestIntro = "선비들의 정신이 살아 있음을 느낄 수 있음";
//    String mngAgcTel = "062-672-0660";
//    String mngAgcNm = "광주광역시공원관리사무소";
//    //when
//    TouristAttractionsResponseDto touristAttractionsResponseDto1 = new TouristAttractionsResponseDto(new TouristAttractions(
//            id,
//            tourDestNm,
//            "",
//            addrRoad,
//            addrJibun,
//            lat,
//            lng,
//            area,
//            publicConvFcltInfo,
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            0,
//            0,
//            tourDestIntro,
//            mngAgcTel,
//            mngAgcNm,
//            ""));
//    private String toJson(Object obj) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.writeValueAsString(obj);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Error converting object to JSON", e);
//        }
//    }
//}