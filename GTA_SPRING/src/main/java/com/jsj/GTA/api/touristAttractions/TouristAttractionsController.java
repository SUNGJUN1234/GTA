package com.jsj.GTA.api.touristAttractions;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TouristAttractionsController {

    private final TouristAttractionsService touristAttractionsService;

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

    /**
     * 전체 관광지 조회
     *
     * @return List<TouristAttractionsListResponseDto>
     */
    @GetMapping("/api/v1/touristAttractions")
    public List<TouristAttractionsListResponseDto> findAllDesc() {
        return touristAttractionsService.findAllDesc();
    }

    /**
     * 스탬프(stampsId)에 해당하는 하나의 관광지 조회
     *
     * @return TouristAttractionsResponseDto
     */
    @GetMapping("/api/v1/touristAttractions/stamps/{stampsId}")
    public TouristAttractionsResponseDto findByStampsIdDesc(@PathVariable Long stampsId) throws IOException {
        return touristAttractionsService.findByStampsIdDesc(stampsId);
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

    /**
     * 주어진 좌표(lat,lng)를 가진 관광지 조회
     *
     * @return List<TouristAttractionsResponseDto>
     */
    @GetMapping("/api/v1/touristAttractions/coordinate/{lat}/{lng}")
    public TouristAttractionsResponseDto findByCoordinate(@PathVariable double lat, @PathVariable double lng) throws IOException {
        return touristAttractionsService.findByCoordinate(lat, lng);
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

}
