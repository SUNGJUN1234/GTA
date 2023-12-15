
package com.jsj.GTA.controller;

import com.jsj.GTA.domain.touristAttractions.TouristAttractionsListResponseDto;
import com.jsj.GTA.domain.touristAttractions.TouristAttractionsResponseDto;
import com.jsj.GTA.service.TouristAttractionsService;
import com.jsj.GTA.service.GeospatialService;
import com.jsj.GTA.domain.touristAttractions.redis.TouristAttractionsGeoResponseDto;
import com.jsj.GTA.domain.touristAttractions.redis.TouristAttractionsResponseRedisDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Tag(name = "관광지 API", description = "관광지에 대한 controller")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/touristAttractions")
public class TouristAttractionsController {

    private final TouristAttractionsService touristAttractionsService;
    private final GeospatialService geospatialService;

    /**
     * 관광지 id로 조회 -> v3
     *
     * @param touristAttractionsId
     * @return TouristAttractionsResponseDto
     */
    @GetMapping("/v1/touristAttractions/{touristAttractionsId}")
    public TouristAttractionsResponseDto findById(@PathVariable String touristAttractionsId) throws IOException {
        return touristAttractionsService.findById(touristAttractionsId);
    }
    @GetMapping("/v2/touristAttractions/{touristAttractionsId}")
    public TouristAttractionsResponseRedisDto findByIdWithRedis(@PathVariable String touristAttractionsId) throws IOException {
        return touristAttractionsService.findByIdWithRedis(touristAttractionsId);
    }
    @Operation(summary = "관광지 한 곳 조회", description = "관광지 한 곳의 id를 받고 관광지 id에 해당하는 관광지 조회")
    @Parameter(name = "touristAttractionsId", description = "관광지 id")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = TouristAttractionsResponseRedisDto.class)))
    @GetMapping("/v3/findTouristAttractions/{touristAttractionsId}")
    public TouristAttractionsResponseRedisDto findByIdWithGeo(@PathVariable String touristAttractionsId) throws IOException {
        TouristAttractionsResponseRedisDto dto = touristAttractionsService.findByIdWithRedis(touristAttractionsId);
//        geospatialService.save(dto.getId(), dto.getLat(), dto.getLng());
        return dto;
    }

    /**
     * 전체 관광지 조회 -> v3
     *
     * @return List<TouristAttractionsListResponseDto>
     */
    @GetMapping("/v1")
    public List<TouristAttractionsListResponseDto> findAllDesc() {
        return touristAttractionsService.findAllDesc();
    }
    @GetMapping("/v2")
    public List<TouristAttractionsResponseRedisDto> findAllDescWithRedis() {
        return touristAttractionsService.findAllDescWithRedis();
    }
    @Operation(summary = "관광지 전체 조회(api로 접근)", description = "관광지 전체 조회, 공공 api에서 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = TouristAttractionsListResponseDto.class)))
    @GetMapping("/v3/findAll/api")
    public List<TouristAttractionsListResponseDto> findAllDesc2() {
        return touristAttractionsService.findAllDesc();
    }
    @Operation(summary = "관광지 전체 조회(redis로 접근)", description = "관광지 전체 조회. 이미지 url을 포함한 캐시 스토어에서 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = TouristAttractionsResponseRedisDto.class)))
    @GetMapping("/v3/findAll/cache")
    public List<TouristAttractionsResponseRedisDto> findAllDescWithRedis2() {
        return touristAttractionsService.findAllDescWithRedis();
    }

    /**
     * 스탬프(stampsId)에 해당하는 하나의 관광지 조회 -> v3
     *
     * @return TouristAttractionsResponseDto
     */
    @GetMapping("/v1/stamps/{stampsId}")
    public TouristAttractionsResponseDto findByStampsId(@PathVariable Long stampsId) throws IOException {
        return touristAttractionsService.findByStampsId(stampsId);
    }
    @GetMapping("/v2/stamps/{stampsId}")
    public TouristAttractionsResponseRedisDto findByStampsIdWithRedis(@PathVariable Long stampsId) throws IOException {
        return touristAttractionsService.findByStampsIdWithRedis(stampsId);
    }
    @Operation(summary = "스탬프 하나에 대응되는 관광지 조회", description = "스탬프 하나의 id를 받고 스탬프를 발급한 관광지 조회")
    @Parameter(name = "stampsId", description = "스탬프 id")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = TouristAttractionsResponseRedisDto.class)))
    @GetMapping("/v3/findStamps/{stampsId}")
    public TouristAttractionsResponseRedisDto findByStampsIdWithRedis2(@PathVariable Long stampsId) throws IOException {
        return touristAttractionsService.findByStampsIdWithRedis(stampsId);
    }

    /**
     * user1명이 가지고 있는 스탬프 전체의 관광지 조회 -> v3
     *
     * @return List<TouristAttractionsListResponseDto>
     */
    @GetMapping("/v1/users/{usersId}")
    public List<TouristAttractionsListResponseDto> findByUserIdDesc(@PathVariable Long usersId) {
        return touristAttractionsService.findByUserIdDesc(usersId);
    }
    @GetMapping("/v2/users/{usersId}")
    public List<TouristAttractionsResponseRedisDto> findByUserIdDescWithRedis(@PathVariable Long usersId) throws IOException {
        return touristAttractionsService.findByUserIdDescWithRedis(usersId);
    }
    @Operation(summary = "사용자가 보유한 스탬프들에 대응되는 관광지 조회", description = "사용자 한 명의 id를 받고 보유한 스탬프 id에 대응되는 관광지 조회")
    @Parameter(name = "usersId", description = "사용자 id")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = TouristAttractionsResponseRedisDto.class)))
    @GetMapping("/v3/findUsers/{usersId}")
    public List<TouristAttractionsResponseRedisDto> findByUserIdDescWithRedis2(@PathVariable Long usersId) throws IOException {
        return touristAttractionsService.findByUserIdDescWithRedis(usersId);
    }

    /**
     * 주어진 좌표(lat,lng)를 가진 관광지 조회 -> v3
     *
     * @return List<TouristAttractionsResponseDto>
     */
    @GetMapping("/v1/coordinate/{lat}/{lng}")
    public TouristAttractionsResponseDto findByCoordinate(@PathVariable double lat, @PathVariable double lng) throws IOException {
        return touristAttractionsService.findByCoordinate(lat, lng);
    }
    @GetMapping("/v2/coordinate/{lat}/{lng}")
    public TouristAttractionsResponseRedisDto findByCoordinateWithRedis(@PathVariable double lat, @PathVariable double lng) throws IOException {
        return touristAttractionsService.findByCoordinateWithRedis(lat, lng);
    }
    @Operation(summary = "좌표에 대응되는 관광지 조회", description = "좌표 정보를 받고 좌표 정보에 해당하는 관광지 조회")
    @Parameter(name = "lat", description = "관광지 lat")
    @Parameter(name = "lng", description = "관광지 lng")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = TouristAttractionsResponseRedisDto.class)))
    @GetMapping("/v3/find/{lat}/{lng}")
    public TouristAttractionsResponseRedisDto findByCoordinateWithRedis2(@PathVariable double lat, @PathVariable double lng) throws IOException {
        return touristAttractionsService.findByCoordinateWithRedis(lat, lng);
    }

    /**
     * 주어진 좌표(lat,lng)로부터 가까운 count 개의 관광지 조회 -> v3
     *
     * @return List<TouristAttractionsListResponseDto>
     */
    @GetMapping("/v1/coordinate/near/{count}/{lat}/{lng}")
    public List<TouristAttractionsListResponseDto> findByNearCoordinate(@PathVariable int count, @PathVariable double lat, @PathVariable double lng) {
        return touristAttractionsService.findByNearCoordinate(count, lat, lng);
    }
    @GetMapping("/v2/coordinate/near/{count}/{lat}/{lng}")
    public List<TouristAttractionsResponseRedisDto> findByNearCoordinateWithRedis(@PathVariable int count, @PathVariable double lat, @PathVariable double lng) throws IOException {
        return touristAttractionsService.findByNearCoordinateWithRedis(count, lat, lng);
    }
    @Operation(summary = "좌표와 가까운 관광지 조회", description = "좌표 정보를 받고 좌표 정보와 가까운 관광지 count 수만큼 조회")
    @Parameter(name = "count", description = "조회할 관광지 수")
    @Parameter(name = "lat", description = "현재 lat")
    @Parameter(name = "lng", description = "현재 lng")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = TouristAttractionsGeoResponseDto.class)))
    @GetMapping("/v3/find/near/{count}/{lat}/{lng}")
    public List<TouristAttractionsGeoResponseDto> findByNearCoordinateWithGeo(@PathVariable int count, @PathVariable double lat, @PathVariable double lng) throws IOException {
        // redis 로 부터 좌표 정보를 불러오고
        GeoResults<RedisGeoCommands.GeoLocation<String>> radius =  geospatialService.findGeoFromLatAndLng(count, lat, lng);
        // 좌표 정보에 있는 관광지 Id 에 부합하는 관광지 리스트 불러오기
        return geospatialService.findTouristAttractionsByGeo(radius);
    }
}
