package com.jsj.GTA.service;

import com.jsj.GTA.domain.stamps.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
// 트랜잭션 범위는 유지, 조회기능만 남겨서 조회 속도 개선 (등록,수정,삭제 기능이 전혀 없는 서비스 메소드에서 사용) 기본 세팅을 하고, readOnly 가 아닌 경우만 따로 명시
public class StampsService {

    private final Logger LOGGER = LoggerFactory.getLogger(StampsService.class);
    private final StampsRepository stampsRepository;

    /**
     * 스탬프 저장(스탬프 찍기)
     */
    @Transactional
    public Long save(StampsSaveRequestDto requestDto) {
        LOGGER.info("[save] save data : {}", requestDto);
        return stampsRepository.save(requestDto.toEntity()).getId();
    }

    public boolean isDuplicate(StampsSaveRequestDto dto) {
        // 사용자 아이디, 관광지 아이디 기반으로 조회
        if(stampsRepository.findByUserIdAndTouristAttractionsId(dto.getUsersId(), dto.getTouristAttractionsId()).isPresent()) {
            LOGGER.info("[isDuplicate] true");
            return true; // 중복o
        } else {
            LOGGER.info("[isDuplicate] false");
            return false; // 중복x
        }
    }

    /**
     * 스탬프 id로 조회
     *
     * @param id
     * @return StampsResponseDto
     */
    public StampsResponseDto findById(Long id) {
        LOGGER.info("[findById] request data : {}", id);
        Optional<Stamps> entity = stampsRepository.findById(id);
        StampsResponseDto dto;
        if(entity.isPresent()) {
            LOGGER.info("[findById] Data dose existed.");
            dto = new StampsResponseDto(entity.get());
        } else {
            LOGGER.info("[findById] Data dose not existed.");
            dto = new StampsResponseDto(new Stamps());
        }
        return dto;
    }

    /**
     * 전체 스탬프 조회
     *
     * @return List<StampsResponseDto>
     */
    public List<StampsListResponseDto> findAllDesc() {
        return stampsRepository.findAllDesc().stream()
                .map(StampsListResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * user1명의 전체 스탬프 조회
     *
     * @return List<StampsResponseDto>
     */
    public List<StampsListResponseDto> findByUserIdDesc(Long id) {
        LOGGER.info("[findByUserIdDesc] request data: {}", id);
        List<StampsListResponseDto> dto = stampsRepository.findByUserIdDesc(id).stream()
                .map(StampsListResponseDto::new)
                .collect(Collectors.toList());
        if(dto.size() == 0) {
            LOGGER.info("[findByUserIdDesc] Data dose not existed.");
        } else {
            LOGGER.info("[findByUserIdDesc] Data dose existed.");
        }
        return dto;
    }

    /**
     * user 1 명의 스탬프 개수 조회
     *
     * @param id
     * @return
     */
    public int getStampsCount(Long id) {
        return stampsRepository.getUsersStampsCount(id);
    }

    /**
     * 스탬프개수별 user 내림차순
     *
     * @return List<UsersStampCountDto>
     */
    public List<UsersStampCountDto> findByUsersRankWithStampCountDesc() {
        List<UsersStampCountDto> dto = stampsRepository.findByUsersRankWithStampCountDesc();
        if(dto.size() == 0) {
            LOGGER.info("[findByUsersRankWithStampCountDesc] Data dose not existed.");
        } else {
            LOGGER.info("[findByUsersRankWithStampCountDesc] Data dose existed.");
        }
        return dto;
    }

    /**
     * 스탬프개수별 user limit 명 내림차순
     *
     * @return List<UsersStampCountDto>
     */
    public List<UsersStampCountDto> findByUsersRankWithStampCountLimitDesc(int limit) {
        List<UsersStampCountDto> dto = stampsRepository.findByUsersRankWithStampCountLimitDesc(limit);
        if(dto.size() == 0) {
            LOGGER.info("[findByUsersRankWithStampCountLimitDesc] Data dose not existed.");
        } else {
            LOGGER.info("[findByUsersRankWithStampCountLimitDesc] Data dose existed.");
        }
        return dto;
    }

    /**
     * touristAttraction 하나의 전체 스탬프 조회
     *
     * @return List<StampsResponseDto>
     */
    public List<StampsListResponseDto> findByTouristAttractionsIdDesc(String id) {
        List<StampsListResponseDto> dto = stampsRepository.findByTouristAttractionsIdDesc(id).stream()
                .map(StampsListResponseDto::new)
                .collect(Collectors.toList());
        if(dto.size() == 0) {
            LOGGER.info("[findByTouristAttractionsIdDesc] Data dose not existed.");
        } else {
            LOGGER.info("[findByTouristAttractionsIdDesc] Data dose existed.");
        }
        return dto;
    }


    /**
     * 지난 달 touristAttraction 하나의 전체 스탬프 조회
     * String lastMonth 를 Month 까지 가공
     * @return List<StampsResponseDto>
     */
//    public List<StampsListResponseDto> findByTouristAttractionsIdDescLastMonth(String id) {
//        return stampsRepository.findByTouristAttractionsIdDesc(id).stream()
//                .map(StampsListResponseDto::new)
//                .collect(Collectors.toList());
//    }

    /**
     * 지난 달 touristAttraction 전체 스탬프 조회
     * @return List<StampsResponseDto>
     */

    /**
     * 이번 달 touristAttraction 하나의 전체 스탬프 조회
     * @return List<StampsResponseDto>
     */

    /**
     * 이번 달 touristAttraction 전체 스탬프 조회
     * @return List<StampsResponseDto>
     */

    /**
     * 지난 달 user1명의 전체 스탬프 조회
     * @return List<StampsResponseDto>
     */

    /**
     * 지난 달 user 전체 스탬프 조회
     * @return List<StampsResponseDto>
     */

    /**
     * 이번 달 user1명의 전체 스탬프 조회
     * @return List<StampsResponseDto>
     */

    /**
     * 이번 달 user 전체 스탬프 조회
     * @return List<StampsResponseDto>
     */

    /**
     * 스탬프 삭제
     */
    @Transactional
    public void delete(Long id) {
        Stamps stamps = stampsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 스탬프가 없습니다. id = " + id));
        stampsRepository.delete(stamps);
    }

}
