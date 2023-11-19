package com.jsj.GTA.api.touristAttractions;

import com.jsj.GTA.api.touristAttractions.redis.GeospatialService;
import com.jsj.GTA.api.touristAttractions.redis.TouristAttractionsGeoResponseDto;
import com.jsj.GTA.api.touristAttractions.redis.TouristAttractionsResponseRedisDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TouristAttractionsController {

    private final TouristAttractionsService touristAttractionsService;
    private final GeospatialService geospatialService;

    /**
     * 관광지 id로 조회
     *
     * @param touristAttractionsId
     * @return TouristAttractionsResponseDto
     */
    @GetMapping("/api/v1/touristAttractions/touristAttractions/{touristAttractionsId}")
    public TouristAttractionsResponseDto findById(@PathVariable String touristAttractionsId) throws IOException {
        return touristAttractionsService.findById(touristAttractionsId);
    }
    @GetMapping("/api/v2/touristAttractions/touristAttractions/{touristAttractionsId}")
    public TouristAttractionsResponseRedisDto findByIdWithRedis(@PathVariable String touristAttractionsId) throws IOException {
        return touristAttractionsService.findByIdWithRedis(touristAttractionsId);
    }
    @GetMapping("/api/v3/touristAttractions/touristAttractions/{touristAttractionsId}")
    public TouristAttractionsResponseRedisDto findByIdWithGeo(@PathVariable String touristAttractionsId) throws IOException {
        TouristAttractionsResponseRedisDto dto = touristAttractionsService.findByIdWithRedis(touristAttractionsId);
        geospatialService.save(dto.getId(), dto.getLat(), dto.getLng());
        return dto;
    }
    /**
     * 전체 관광지 조회
     *
     * @return List<TouristAttractionsListResponseDto>
     */
    @GetMapping("/api/v1/touristAttractions")
    public List<TouristAttractionsListResponseDto> findAllDesc() {
        return touristAttractionsService.findAllDesc();
    }
    @GetMapping("/api/v2/touristAttractions")
    public List<TouristAttractionsResponseRedisDto> findAllDescWithRedis() {
        return touristAttractionsService.findAllDescWithRedis();
    }


    /**
     * 스탬프(stampsId)에 해당하는 하나의 관광지 조회
     *
     * @return TouristAttractionsResponseDto
     */
    @GetMapping("/api/v1/touristAttractions/stamps/{stampsId}")
    public TouristAttractionsResponseDto findByStampsId(@PathVariable Long stampsId) throws IOException {
        return touristAttractionsService.findByStampsId(stampsId);
    }
    @GetMapping("/api/v2/touristAttractions/stamps/{stampsId}")
    public TouristAttractionsResponseRedisDto findByStampsIdWithRedis(@PathVariable Long stampsId) throws IOException {
        return touristAttractionsService.findByStampsIdWithRedis(stampsId);
    }
    /**
     * user1명이 가지고 있는 스탬프 전체의 관광지 조회
     *
     * @return List<TouristAttractionsListResponseDto>
     */
    @GetMapping("/api/v1/touristAttractions/users/{usersId}")
    public List<TouristAttractionsListResponseDto> findByUserIdDesc(@PathVariable Long usersId) {
        return touristAttractionsService.findByUserIdDesc(usersId);
    }
    @GetMapping("/api/v2/touristAttractions/users/{usersId}")
    public List<TouristAttractionsResponseRedisDto> findByUserIdDescWithRedis(@PathVariable Long usersId) throws IOException {
        return touristAttractionsService.findByUserIdDescWithRedis(usersId);
    }

    /**
     * 주어진 좌표(lat,lng)를 가진 관광지 조회
     *
     * @return List<TouristAttractionsResponseDto>
     */
    @GetMapping("/api/v1/touristAttractions/coordinate/{lat}/{lng}")
    public TouristAttractionsResponseDto findByCoordinate(@PathVariable double lat, @PathVariable double lng) throws IOException {
        return touristAttractionsService.findByCoordinate(lat, lng);
    }
    @GetMapping("/api/v2/touristAttractions/coordinate/{lat}/{lng}")
    public TouristAttractionsResponseRedisDto findByCoordinateWithRedis(@PathVariable double lat, @PathVariable double lng) throws IOException {
        return touristAttractionsService.findByCoordinateWithRedis(lat, lng);
    }
    /**
     * 주어진 좌표(lat,lng)로부터 가까운 count 개의 관광지 조회
     *
     * @return List<TouristAttractionsListResponseDto>
     */
    @GetMapping("/api/v1/touristAttractions/coordinate/near/{count}/{lat}/{lng}")
    public List<TouristAttractionsListResponseDto> findByNearCoordinate(@PathVariable int count, @PathVariable double lat, @PathVariable double lng) {
        return touristAttractionsService.findByNearCoordinate(count, lat, lng);
    }
    @GetMapping("/api/v2/touristAttractions/coordinate/near/{count}/{lat}/{lng}")
    public List<TouristAttractionsResponseRedisDto> findByNearCoordinateWithRedis(@PathVariable int count, @PathVariable double lat, @PathVariable double lng) throws IOException {
        return touristAttractionsService.findByNearCoordinateWithRedis(count, lat, lng);
    }
    @GetMapping("/api/v3/touristAttractions/coordinate/near/{count}/{lat}/{lng}")
    public List<TouristAttractionsGeoResponseDto> findByNearCoordinateWithGeo(@PathVariable int count, @PathVariable double lat, @PathVariable double lng) throws IOException {
        // redis 로 부터 좌표 정보를 불러오고
        GeoResults<RedisGeoCommands.GeoLocation<String>> radius =  geospatialService.findGeoFromLatAndLng(count, lat, lng);
        // 좌표 정보에 있는 관광지 Id 에 부합하는 관광지 리스트 불러오기
        return geospatialService.findTouristAttractionsByGeo(radius);
    }
}
