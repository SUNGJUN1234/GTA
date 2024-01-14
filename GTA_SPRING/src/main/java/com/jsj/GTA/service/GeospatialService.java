package com.jsj.GTA.service;

import com.jsj.GTA.domain.touristAttractions.redis.TouristAttractionsGeoResponseDto;
import com.jsj.GTA.domain.touristAttractions.redis.TouristAttractionsRedisRepository;
import com.jsj.GTA.domain.touristAttractions.redis.TouristAttractionsResponseRedisDto;
import com.jsj.GTA.domain.touristAttractions.TouristAttractionsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GeospatialService {

    private final Logger LOGGER = LoggerFactory.getLogger(GeospatialService.class);
    private final TouristAttractionsRedisRepository touristAttractionsRedisRepository;
    private final TouristAttractionsRepository touristAttractionsRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private String key = "geopoints";

    /**
     * redis 에 좌표 정보를 저장하기
     *
     * @param touristAttractionsId
     * @param lat
     * @param lng
     * @return
     */
    public String save(String touristAttractionsId, double lat, double lng) {
        LOGGER.info("[save] request data touristAttractionsId : {} lat: {} lng : {}", touristAttractionsId, lat, lng);
        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo(); // Redis 에 저장할 좌표 자료구조
        Point point = new Point(lng, lat); // 좌표값을 담고(Point 는 lng,lat 순서)
        try {
            geoOperations.add(key, point, touristAttractionsId); // 자료구조에 좌표담기
            return "success";
        } catch (Exception e) {
            LOGGER.error("[save] save fail", e);
            return "fail";
        }
    }

    /**
     * redis 로 부터 좌표 정보 리스트 가져오기
     *
     * @param count 개수
     * @param lat   y
     * @param lng   x
     * @return GeoResults<RedisGeoCommands.GeoLocation < String>> 위도 경도를 기반으로 가까운 거리에 있는 좌표 정보
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> findGeoFromLatAndLng(int count, double lat, double lng) {
        LOGGER.info("[findGeoFromLatAndLng] request data count : {} lat: {} lng : {}", count, lat, lng);
        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo(); // Redis 에 저장할 좌표 자료구조
        Point point = new Point(lng, lat); // 좌표값을 담고(Point 는 lng,lat 순서)
        Metric metric = RedisGeoCommands.DistanceUnit.KILOMETERS; // 거리에 대한 객체
        Distance distance = new Distance(11000, metric); // 몇 키로미터 반경을 구하는지
        Circle circle = new Circle(point, distance); // point 를 기준으로 distance 반경

        // 경도, 위도(lat, lng) 기준으로 반경 200 km를 찾고,
        // 가장 가까운 순서로 count 수 만큼 위치정보 추출하기

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands
                .GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .sortAscending()
                .limit(count);

        // 거리와 좌표정보 같이 반환
        GeoResults<RedisGeoCommands.GeoLocation<String>> radius = geoOperations
                .radius(key, circle, args);

        if (radius == null) {
            LOGGER.info("[findGeoFromLatAndLng] No Data radius Geo");
            return null;
        }
        LOGGER.info("[findGeoFromLatAndLng] Geo Data existed");
        return radius;
    }

    public List<TouristAttractionsGeoResponseDto> findTouristAttractionsByGeo(GeoResults<RedisGeoCommands.GeoLocation<String>> radius) {
        LOGGER.info("[findTouristAttractionsByGeo] request data");
        if (radius == null) {
            LOGGER.info("[findTouristAttractionsByGeo] No Data radius Geo");
            return null;
        }
        LOGGER.info("[findTouristAttractionsByGeo] Geo Data existed");
        List<TouristAttractionsGeoResponseDto> touristAttractionsGeoResponseDtoList = new ArrayList<>();
        radius.forEach(geoLocationGeoResult -> {
            // 좌표 정보를 담고 있는 변수
            RedisGeoCommands.GeoLocation<String> content = geoLocationGeoResult.getContent();
            // 관광지 번호
            String id = content.getName();
            // 좌표 정보
            Point contentPoint = content.getPoint();
            // 거리
            Distance contentDistance = geoLocationGeoResult.getDistance();
            LOGGER.info("[findTouristAttractionsByGeo] Geo Data touristAttractionsId : {} point : {} distance : {}", id, contentPoint, contentDistance);

            // 얻은 데이터를 기반으로 Redis 조회
            Optional<TouristAttractionsResponseRedisDto> touristAttraction = touristAttractionsRedisRepository.findById(id);
            if (touristAttraction.isPresent()) {
                TouristAttractionsGeoResponseDto dto = new TouristAttractionsGeoResponseDto(touristAttraction.get(), contentDistance);
                touristAttractionsGeoResponseDtoList.add(dto);
            }
        });
        return touristAttractionsGeoResponseDtoList;
    }
// 특정 key 에 대한 좌표 정보 삭제
//geoOperations.remove(key, "1");
}
