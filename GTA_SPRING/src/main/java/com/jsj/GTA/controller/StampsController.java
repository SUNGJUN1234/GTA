package com.jsj.GTA.controller;

import com.jsj.GTA.domain.stamps.StampsListResponseDto;
import com.jsj.GTA.domain.stamps.StampsResponseDto;
import com.jsj.GTA.domain.stamps.StampsSaveRequestDto;
import com.jsj.GTA.domain.stamps.UsersStampCountDto;
import com.jsj.GTA.service.StampsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StampsController {
    // API 를 만들기 위해 3개의 클래스가 필요함
    // Request 데이터를 받을 Dto
    // API 요청을 받을 Controller
    // 트랜잭션, 도메인 기능 간의 순서를 보장하는 Service - 비즈니스 로직을 처리하지 않음

    private final StampsService stampsService;

    /**
     * 스탬프 찍기
     * @param requestDto
     * @return 스탬프 id
     */
    @PostMapping("/api/v1/stamps")
    public Long save(@RequestBody StampsSaveRequestDto requestDto) {
        return stampsService.save(requestDto);
    }

    /**
     * 스탬프의 정보 조회
     * @param stampsId
     * @return 스탬프
     */
    @GetMapping("/api/v1/stamps/{stampsId}")
    public StampsResponseDto findById(@PathVariable Long stampsId) {
        return stampsService.findById(stampsId);
    }

    /**
     * user 1명의 스탬프 목록 조회
     * @param usersId
     * @return List<StampsListResponseDto>
     */
    @GetMapping("/api/v1/stamps/{usersId}")
    public List<StampsListResponseDto> findByUserIdDesc(@PathVariable Long usersId) {
        return stampsService.findByUserIdDesc(usersId);
    }

    /**
     * 관광지(touristAttraction) 하나의 전체 스탬프 조회
     * @return List<StampsResponseDto>
     */
    @GetMapping("/api/v1/stamps/{touristAttractionsId}")
    public List<StampsListResponseDto> findByTouristAttractionsIdDesc(@PathVariable String touristAttractionsId) {
        return stampsService.findByTouristAttractionsIdDesc(touristAttractionsId);
    }

    /**
     * 스탬프개수별 user 내림차순
     * @return UsersStampCountDto
     */
    @GetMapping("/api/v1/stamps/rank")
    public List<UsersStampCountDto> findByUsersRankWithStampCountDesc() {
        return stampsService.findByUsersRankWithStampCountDesc();
    }

    /**
     * 스탬프개수별 user limit 명 내림차순
     * @return List<UsersStampCountDto>
     */
    @GetMapping("/api/v1/stamps/rank/{limit}")
    public List<UsersStampCountDto> findByUsersRankWithStampCountLimitDesc(@PathVariable int limit) {
        return stampsService.findByUsersRankWithStampCountLimitDesc(limit);
    }
}
