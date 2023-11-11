package com.jsj.GTA.api.touristAttractions;

import com.jsj.GTA.domain.stamps.*;
import com.jsj.GTA.util.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
// 트랜잭션 범위는 유지, 조회기능만 남겨서 조회 속도 개선 (등록,수정,삭제 기능이 전혀 없는 서비스 메소드에서 사용) 기본 세팅을 하고, readOnly 가 아닌 경우만 따로 명시
public class TouristAttractionsService {

    private final TouristAttractionsRepository touristAttractionsRepository;

    private final StampsRepository stampsRepository;

    /**
     * 관광지 id로 조회
     *
     * @param id
     * @return TouristAttractionsResponseDto
     */
    public TouristAttractionsResponseDto findById(String id) throws IOException {
        TouristAttractions entity = touristAttractionsRepository.findById(id);
        if (entity != null) new IllegalArgumentException("해당 스탬프가 없습니다. id = " + id);

        return new TouristAttractionsResponseDto(entity);
    }

    /**
     * 전체 관광지 조회
     *
     * @return List<TouristAttractionsListResponseDto>
     */
    public List<TouristAttractionsListResponseDto> findAllDesc() {
        return touristAttractionsRepository.findAllDesc().stream()
                .map(TouristAttractionsListResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 스탬프에 해당하는 하나의 관광지 조회
     *
     * @return TouristAttractionsResponseDto
     */
    public TouristAttractionsResponseDto findByStampsIdDesc(Long id) throws IOException {
        Stamps stamps = stampsRepository.findById(id).orElse(null);
        if (stamps == null) {
            new IllegalArgumentException("해당 스탬프가 없습니다. id = " + id);
            return null;
        }
        TouristAttractions touristAttractions;
        touristAttractions = touristAttractionsRepository.findByStampsIdDesc(stamps.getTouristAttractionsId());
        return new TouristAttractionsResponseDto(touristAttractions);
    }

    /**
     * user1명이 가지고 있는 스탬프 전체의 관광지 조회
     *
     * @return List<TouristAttractionsListResponseDto>
     */
    public List<TouristAttractionsListResponseDto> findByUserIdDesc(Long id) {
        // user가 가진 스탬프 전부 구하기
        List<Stamps> stamps = stampsRepository.findByUserIdDesc(id);
        // 스탬프가 가진 관광지를 id별로 구하기
        List<TouristAttractions> result = new ArrayList<>();
        for (int i = 0; i < stamps.size(); i++) {
            String touristAttractionsId = stamps.get(i).getTouristAttractionsId();
            result.add(touristAttractionsRepository.findById(touristAttractionsId));
        }
        return result.stream()
                .map(TouristAttractionsListResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 주어진 좌표를 가진 관광지 조회
     *
     * @return List<TouristAttractionsResponseDto>
     */
    public TouristAttractionsResponseDto findByCoordinate(double lat, double lng) throws IOException {
        TouristAttractions touristAttractions;
        touristAttractions = touristAttractionsRepository.findByCoordinate(lat, lng);
        return new TouristAttractionsResponseDto(touristAttractions);
    }

    /**
     * 주어진 좌표로부터 가까운 3개의 관광지 조회
     *
     * @return List<TouristAttractionsListResponseDto>
     */
    public List<TouristAttractionsListResponseDto> findByNearCoordinate(int count, double lat, double lng) {
        // 전체 관광지 조회,
        // list 에 담고,
        List<TouristAttractionsListResponseDto> responseList =
                touristAttractionsRepository.findAllDesc().stream()
                        .map(TouristAttractionsListResponseDto::new)
                        .collect(Collectors.toList());
        // 좌표를 비교하는 알고리즘 사용하여 값을 반환
        return Coordinate.findClosestCoordinates(responseList, count, lat, lng);
    }


}