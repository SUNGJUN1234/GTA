package com.jsj.GTA.controller;

import com.jsj.GTA.domain.stamps.StampsListResponseDto;
import com.jsj.GTA.domain.stamps.StampsResponseDto;
import com.jsj.GTA.domain.stamps.StampsSaveRequestDto;
import com.jsj.GTA.domain.stamps.UsersStampCountDto;
import com.jsj.GTA.service.StampsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stamps")
public class StampsController {
    // API 를 만들기 위해 3개의 클래스가 필요함
    // Request 데이터를 받을 Dto
    // API 요청을 받을 Controller
    // 트랜잭션, 도메인 기능 간의 순서를 보장하는 Service - 비즈니스 로직을 처리하지 않음

    private final StampsService stampsService;

    /**
     * 스탬프 찍기 -> v3
     * @param requestDto
     * @return 스탬프 id
     */
    @PostMapping("/v1")
    public Long save(@RequestBody StampsSaveRequestDto requestDto) {
        return stampsService.save(requestDto);
    }
    @PostMapping("/v2")
    public ResponseEntity<?> validateAndSave(@RequestBody StampsSaveRequestDto requestDto) {
        if(stampsService.isDuplicate(requestDto)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 이 관광지에서 스탬프를 발급받았습니다");
        }
        return ResponseEntity.ok(stampsService.save(requestDto)); // 스탬프 아이디 반환
    }
    @Operation(summary = "스탬프 생성", description = "스탬프Dto를 받고 스탬프를 생성")
    @Parameter(name = "StampsSaveRequestDto", description = "생성할 스탬프Dto")
    @ApiResponse(responseCode = "200", description = "생성 성공", content = @Content(schema = @Schema(implementation = Long.class)))
    @ApiResponse(responseCode = "409", description = "생성 실패(중복)", content = @Content(schema = @Schema(implementation = String.class)))
    @PostMapping("/v3/save")
    public ResponseEntity<?> validateAndSave2(@RequestBody StampsSaveRequestDto requestDto) {
        if(stampsService.isDuplicate(requestDto)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 이 관광지에서 스탬프를 발급받았습니다");
        }
        return ResponseEntity.ok(stampsService.save(requestDto)); // 스탬프 아이디 반환
    }

    /**
     * 스탬프의 정보 조회 -> v3
     * @param stampsId
     * @return 스탬프
     */
    @GetMapping("/v1/{stampsId}")
    public StampsResponseDto findById(@PathVariable Long stampsId) {
        return stampsService.findById(stampsId);
    }
    @GetMapping("/v2/{stampsId}")
    public ResponseEntity<?> findById2(@PathVariable Long stampsId) {
        StampsResponseDto dto = stampsService.findById(stampsId);
        if(dto.getId() != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("스탬프 id 에 해당하는 스탬프 정보가 없습니다");
    }
    @Operation(summary = "스탬프 하나 조회", description = "스탬프 하나의 id를 받고 스탬프 id에 해당하는 스탬프 조회")
    @Parameter(name = "stampsId", description = "스탬프 id")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = StampsResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "조회 실패(존재x)", content = @Content(schema = @Schema(implementation = String.class)))
    @GetMapping("/v3/findStamps/{stampsId}")
    public ResponseEntity<?> findById3(@PathVariable Long stampsId) {
        StampsResponseDto dto = stampsService.findById(stampsId);
        if(dto.getId() != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("스탬프 id 에 해당하는 스탬프 정보가 없습니다");
    }


    /**
     * user 1명의 스탬프 목록 조회 -> v3
     * @param usersId
     * @return List<StampsListResponseDto>
     */
    @GetMapping("/v1/{usersId}")
    public List<StampsListResponseDto> findByUserIdDesc(@PathVariable Long usersId) {
        return stampsService.findByUserIdDesc(usersId);
    }
    @GetMapping("/v2/{usersId}")
    public ResponseEntity<?> findByUserIdDesc2(@PathVariable Long usersId) {
        List<StampsListResponseDto> dto = stampsService.findByUserIdDesc(usersId);
        if(dto.size() != 0) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 Id 에 해당하는 스탬프 정보가 없습니다");
    }
    @Operation(summary = "사용자 id에 대응되는 스탬프 조회", description = "사용자 한 명의 id를 받고 사용자 id에 대응되는 스탬프 조회")
    @Parameter(name = "usersId", description = "사용자 id")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = StampsListResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "조회 실패(존재x)", content = @Content(schema = @Schema(implementation = String.class)))
    @GetMapping("/v3/findUsers/{usersId}")
    public ResponseEntity<?> findByUserIdDesc3(@PathVariable Long usersId) {
        List<StampsListResponseDto> dto = stampsService.findByUserIdDesc(usersId);
        if(dto.size() != 0) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 Id 에 해당하는 스탬프 정보가 없습니다");
    }

    /**
     * 관광지(touristAttraction) 하나의 전체 스탬프 조회 -> v2
     * @return List<StampsResponseDto>
     */
    @GetMapping("/v1/{touristAttractionsId}")
    public List<StampsListResponseDto> findByTouristAttractionsIdDesc(@PathVariable String touristAttractionsId) {
        return stampsService.findByTouristAttractionsIdDesc(touristAttractionsId);
    }
    @GetMapping("/v2/{touristAttractionsId}")
    public ResponseEntity<?> findByTouristAttractionsIdDesc2(@PathVariable String touristAttractionsId) {
        List<StampsListResponseDto> dto = stampsService.findByTouristAttractionsIdDesc(touristAttractionsId);
        if(dto.size() != 0) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("관광지 Id 에 해당하는 스탬프 정보가 없습니다");
    }
    @Operation(summary = "관광지 id에 대응되는 스탬프 조회", description = "관광지 한 곳의 id를 받고 관광지 id에 대응되는 스탬프 조회")
    @Parameter(name = "touristAttractionsId", description = "관광지 id")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = StampsListResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "조회 실패(존재x)", content = @Content(schema = @Schema(implementation = String.class)))
    @GetMapping("/v3/findTouristAttractions/{touristAttractionsId}")
    public ResponseEntity<?> findByTouristAttractionsIdDesc3(@PathVariable String touristAttractionsId) {
        List<StampsListResponseDto> dto = stampsService.findByTouristAttractionsIdDesc(touristAttractionsId);
        if(dto.size() != 0) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("관광지 Id 에 해당하는 스탬프 정보가 없습니다");
    }

    /**
     * 스탬프개수별 user 내림차순
     * @return UsersStampCountDto
     */
    @GetMapping("/v1/rank")
    public List<UsersStampCountDto> findByUsersRankWithStampCountDesc() {
        return stampsService.findByUsersRankWithStampCountDesc();
    }
    @GetMapping("/v2/rank")
    public ResponseEntity<?> findByUsersRankWithStampCountDesc2() {
        List<UsersStampCountDto> dto = stampsService.findByUsersRankWithStampCountDesc();
        if(dto.size() != 0) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("스탬프를 가진 사용자가 없습니다");
    }
    @Operation(summary = "스탬프 개수 별 사용자 내림차순 조회", description = "스탬프 개수 별 사용자 내림차순 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = UsersStampCountDto.class)))
    @ApiResponse(responseCode = "404", description = "조회 실패(사용자 존재x)", content = @Content(schema = @Schema(implementation = String.class)))
    @GetMapping("/v3/find/user/rank")
    public ResponseEntity<?> findByUsersRankWithStampCountDesc3() {
        List<UsersStampCountDto> dto = stampsService.findByUsersRankWithStampCountDesc();
        if(dto.size() != 0) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("스탬프를 가진 사용자가 없습니다");
    }

    /**
     * 스탬프개수별 user limit 명 내림차순 -> v2
     * @return List<UsersStampCountDto>
     */
    @GetMapping("/v1/rank/{limit}")
    public List<UsersStampCountDto> findByUsersRankWithStampCountLimitDesc(@PathVariable int limit) {
        return stampsService.findByUsersRankWithStampCountLimitDesc(limit);
    }
    @GetMapping("/v2/rank/{limit}")
    public ResponseEntity<?> findByUsersRankWithStampCountLimitDesc2(@PathVariable int limit) {
        List<UsersStampCountDto> dto = stampsService.findByUsersRankWithStampCountLimitDesc(limit);
        if (dto.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("스탬프를 가진 사용자가 없습니다");
        }
        return ResponseEntity.ok(dto);
    }
    @Operation(summary = "스탬프 개수 별 사용자 내림차순 조회", description = "스탬프 개수 별 사용자 limit 수만큼 내림차순 조회")
    @Parameter(name = "limit", description = "조회할 사용자 수")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = UsersStampCountDto.class)))
    @ApiResponse(responseCode = "404", description = "조회 실패(사용자 존재x)", content = @Content(schema = @Schema(implementation = String.class)))
    @GetMapping("/v3/find/user/rank/{limit}")
    public ResponseEntity<?> findByUsersRankWithStampCountLimitDesc3(@PathVariable int limit) {
        List<UsersStampCountDto> dto = stampsService.findByUsersRankWithStampCountLimitDesc(limit);
        if (dto.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("스탬프를 가진 사용자가 없습니다");
        }
        return ResponseEntity.ok(dto);
    }
}
