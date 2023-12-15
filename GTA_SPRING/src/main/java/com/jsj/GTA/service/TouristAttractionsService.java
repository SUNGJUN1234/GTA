package com.jsj.GTA.service;

import com.jsj.GTA.domain.touristAttractions.TouristAttractions;
import com.jsj.GTA.domain.touristAttractions.TouristAttractionsListResponseDto;
import com.jsj.GTA.domain.touristAttractions.TouristAttractionsRepository;
import com.jsj.GTA.domain.touristAttractions.TouristAttractionsResponseDto;
import com.jsj.GTA.domain.touristAttractions.redis.TouristAttractionsRedisRepository;
import com.jsj.GTA.domain.touristAttractions.redis.TouristAttractionsResponseRedisDto;
import com.jsj.GTA.domain.stamps.*;
import com.jsj.GTA.util.Coordinate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class TouristAttractionsService {

    private final Logger LOGGER = LoggerFactory.getLogger(TouristAttractionsService.class);

    private final TouristAttractionsRepository touristAttractionsRepository;

    private final StampsRepository stampsRepository;

    private final TouristAttractionsRedisRepository touristAttractionsRedisRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    /**
     * 관광지 id로 조회
     *
     * @param id
     * @return TouristAttractionsResponseDto
     */
    public TouristAttractionsResponseDto findById(String id) throws IOException {
        LOGGER.info("[findById] request data : {}", id);
        TouristAttractions entity = touristAttractionsRepository.findById(id);
        if (entity != null) {
            LOGGER.info("[findById] Data dose not existed.");
        }
        TouristAttractionsResponseDto responseDto = new TouristAttractionsResponseDto(entity);
        LOGGER.info("[findById] TouristAttractionsResponseDto : {}", responseDto);
        return responseDto;
    }

    public TouristAttractionsResponseRedisDto findByIdWithRedis(String id) throws IOException {
        LOGGER.info("[findByIdWithRedis] request data : {}", id);
        Optional<TouristAttractionsResponseRedisDto> cache = touristAttractionsRedisRepository.findById(id);
        // 캐시 데이터가 존재하면 캐시 데이터 반환
        if (cache.isPresent()) {
            LOGGER.info("[findByIdWithRedis] Cache Data existed.");
            return cache.get();
        } else {
            LOGGER.info("[findByIdWithRedis] Cache Data dose not existed.");
        }
        // 캐시 데이터가 존재하지 않으면 캐시 데이터 저장
        // 먼저 데이터를 api 에서 가져오고
        TouristAttractionsResponseDto entity = findById(id);

        if (entity == null) {
            LOGGER.info("[findByIdWithRedis] No Data entity");
            return null;
        }
        // 가져온 데이터가 있으면 redis 에 저장 및 반환
        TouristAttractionsResponseRedisDto redisDto = TouristAttractionsResponseRedisDto.createDto(entity);
        touristAttractionsRedisRepository.save(redisDto);
        return redisDto;
    }

    /**
     * 전체 관광지 조회
     *
     * @return List<TouristAttractionsListResponseDto>
     */
    public List<TouristAttractionsListResponseDto> findAllDesc() {
        LOGGER.info("[findAllDesc] request data");

        List<TouristAttractionsListResponseDto> responseDtoList = touristAttractionsRepository.findAllDesc().stream()
                .map(TouristAttractionsListResponseDto::new)
                .collect(Collectors.toList());
        if (responseDtoList.isEmpty()) {
            LOGGER.info("[findAllDesc] No Data api");
        }
        LOGGER.info("[findAllDesc] ResponseDtoList : {}", responseDtoList);
        return responseDtoList;
    }

    public List<TouristAttractionsResponseRedisDto> findAllDescWithRedis() {
        LOGGER.info("[findAllDescWithRedis] request data");
        Iterator<TouristAttractionsResponseRedisDto> iteratorCache = touristAttractionsRedisRepository.findAll().iterator();
        List<TouristAttractionsResponseRedisDto> cache = new ArrayList<>();
        while (iteratorCache.hasNext()) {
            cache.add(iteratorCache.next());
        }
        // 캐시 데이터가 존재하면 캐시 데이터 반환
        if (!cache.isEmpty()) {
            LOGGER.info("[findAllDescWithRedis] Cache Data existed.");
            return cache;
        } else {
            LOGGER.info("[findAllDescWithRedis] Cache Data dose not existed.");
        }
        // 존재하지 않으면
        // 데이터 조회
        List<TouristAttractionsListResponseDto> entity = touristAttractionsRepository.findAllDesc().stream()
                .map(TouristAttractionsListResponseDto::new)
                .toList();
        if (entity.isEmpty()) {
            LOGGER.info("[findAllDescWithRedis] No Data entity");
        }
        // redis 저장 및 데이터 반환
        List<TouristAttractionsResponseRedisDto> responseRedisDtoList = entity.stream()
                .map(entityList -> modelMapper.map(entityList, TouristAttractionsResponseRedisDto.class))
                .collect(Collectors.toList());
        touristAttractionsRedisRepository.saveAll(responseRedisDtoList);
        return responseRedisDtoList;
    }

    /**
     * 스탬프에 해당하는 하나의 관광지 조회
     *
     * @return TouristAttractionsResponseDto
     */
    public TouristAttractionsResponseDto findByStampsId(Long id) throws IOException {
        LOGGER.info("[findByStampsId] request data : {}", id);
        // 스탬프 조회
        Stamps stamps = stampsRepository.findById(id).orElse(null);
        if (stamps == null) {
            LOGGER.info("[findByStampsId] No Data stamp");
            return null;
        }
        TouristAttractions touristAttractions = touristAttractionsRepository.findByStampsId(stamps.getTouristAttractionsId());
        return new TouristAttractionsResponseDto(touristAttractions);
    }
    public TouristAttractionsResponseRedisDto findByStampsIdWithRedis(Long id) throws IOException {
        LOGGER.info("[findByStampsIdWithRedis] request data : {}", id);
        // 스탬프 조회
        Stamps stamps = stampsRepository.findById(id).orElse(null);
        if (stamps == null) {
            LOGGER.info("[findByStampsId] No Data stamp");
            return null;
        }
        Optional<TouristAttractionsResponseRedisDto> cache = touristAttractionsRedisRepository.findById(stamps.getTouristAttractionsId());
        // 캐시 데이터가 존재하면 캐시 데이터 반환
        if (cache.isPresent()) {
            LOGGER.info("[findByStampsIdWithRedis] Cache Data existed.");
            return cache.get();
        } else {
            LOGGER.info("[findByStampsIdWithRedis] Cache Data dose not existed.");
        }
        // 캐시 데이터가 존재하지 않으면 캐시 데이터 저장
        // 먼저 데이터를 api 에서 가져오고
        TouristAttractionsResponseDto entity = findById(stamps.getTouristAttractionsId());

        if (entity == null) {
            LOGGER.info("[findByStampsIdWithRedis] No Data entity");
            return null;
        }
        // 가져온 데이터가 있으면 redis 에 저장 및 반환
        TouristAttractionsResponseRedisDto redisDto = TouristAttractionsResponseRedisDto.createDto(entity);
        touristAttractionsRedisRepository.save(redisDto);
        return redisDto;
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
        for (Stamps stamp : stamps) {
            String touristAttractionsId = stamp.getTouristAttractionsId();
            result.add(touristAttractionsRepository.findById(touristAttractionsId));
        }
        return result.stream()
                .map(TouristAttractionsListResponseDto::new)
                .collect(Collectors.toList());
    }
    public List<TouristAttractionsResponseRedisDto> findByUserIdDescWithRedis(Long id) throws IOException {
        // user가 가진 스탬프 전부 구하기
        List<Stamps> stamps = stampsRepository.findByUserIdDesc(id);
        // 스탬프가 가진 관광지를 id별로 구하기
        List<TouristAttractionsResponseRedisDto> result = new ArrayList<>();
        for (Stamps stamp : stamps) {
            String touristAttractionsId = stamp.getTouristAttractionsId();
            Optional<TouristAttractionsResponseRedisDto> cache = touristAttractionsRedisRepository.findById(touristAttractionsId);
            // 캐시 데이터가 존재하면 캐시 데이터 반환
            if (cache.isPresent()) {
                LOGGER.info("[findByUserIdDescWithRedis] Cache Data existed.");
                result.add(cache.get());
                continue;
            } else {
                LOGGER.info("[findByUserIdDescWithRedis] Cache Data dose not existed.");
            }
            // 캐시 데이터가 존재하지 않으면 캐시 데이터 저장
            // 먼저 데이터를 api 에서 가져오고
            TouristAttractionsResponseDto entity = findById(touristAttractionsId);

            if (entity == null) {
                LOGGER.info("[findByUserIdDescWithRedis] No Data entity");
                continue;
            }
            // 가져온 데이터가 있으면 redis 에 저장 및 반환
            TouristAttractionsResponseRedisDto redisDto = TouristAttractionsResponseRedisDto.createDto(entity);
            touristAttractionsRedisRepository.save(redisDto);

            result.add(redisDto);
        }
        return result;
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
    public TouristAttractionsResponseRedisDto findByCoordinateWithRedis(double lat, double lng) throws IOException {
        LOGGER.info("[findByCoordinateWithRedis] request data lat : {} lng : {}", lat, lng);
        Optional<TouristAttractionsResponseRedisDto> cache = touristAttractionsRedisRepository.findByLatAndLng(""+lat, ""+lng);
        // 캐시 데이터가 존재하면 캐시 데이터 반환
        if (cache.isPresent()) {
            LOGGER.info("[findByCoordinateWithRedis] Cache Data existed.");
            return cache.get();
        } else {
            LOGGER.info("[findByCoordinateWithRedis] Cache Data dose not existed.");
        }
        // 캐시 데이터가 존재하지 않으면 캐시 데이터 저장
        // 먼저 데이터를 api 에서 가져오고
        TouristAttractionsResponseDto entity = findByCoordinate(lat, lng);

        if (entity == null) {
            LOGGER.info("[findByCoordinateWithRedis] No Data entity");
            return null;
        }
        // 가져온 데이터가 있으면 redis 에 저장 및 반환
        TouristAttractionsResponseRedisDto redisDto = TouristAttractionsResponseRedisDto.createDto(entity);
        touristAttractionsRedisRepository.save(redisDto);
        return redisDto;
    }
    /**
     * 주어진 좌표로부터 가까운 count 의 관광지 조회
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
    public List<TouristAttractionsResponseRedisDto> findByNearCoordinateWithRedis(int count, double lat, double lng) throws IOException {
        LOGGER.info("[findByNearCoordinateWithRedis] request data");
        // 전체 관광지 조회,
        // list 에 담고,
        // 데이터 조회
        List<TouristAttractionsListResponseDto> entity = touristAttractionsRepository.findAllDesc().stream()
                .map(TouristAttractionsListResponseDto::new)
                .toList();
        if (entity.isEmpty()) {
            LOGGER.info("[findByNearCoordinateWithRedis] No Data entity");
            return null;
        }
        // 좌표를 비교하는 알고리즘 사용하여 값을 반환
        List<TouristAttractionsListResponseDto> coordinateResult = Coordinate.findClosestCoordinates(entity, count, lat, lng);
        // 반환한 값을 토대로 새로운 리스트 생성 및 반환
        if(coordinateResult.isEmpty()) {
            LOGGER.info("[findByNearCoordinateWithRedis] No Data coordinateResult");
            return null;
        }
        LOGGER.info("[findByNearCoordinateWithRedis] 좌표 리스트에 해당하는 캐시 데이터 가져오기");
        List<TouristAttractionsResponseRedisDto> result = new ArrayList<>();
        for (TouristAttractionsListResponseDto dto : coordinateResult) {
            String touristAttractionsId = dto.getId();
            Optional<TouristAttractionsResponseRedisDto> cache = touristAttractionsRedisRepository.findById(touristAttractionsId);
            // 캐시 데이터가 존재하면 캐시 데이터 반환
            if (cache.isPresent()) {
                LOGGER.info("[findByNearCoordinateWithRedis] Cache Data existed.");
                result.add(cache.get());
                continue;
            } else {
                LOGGER.info("[findByNearCoordinateWithRedis] Cache Data dose not existed.");
            }
            // 캐시 데이터가 존재하지 않으면 캐시 데이터 저장
            // 먼저 데이터를 api 에서 가져오고
            TouristAttractionsResponseDto touristAttractionsEntity = findById(touristAttractionsId);

            if (touristAttractionsEntity == null) {
                LOGGER.info("[findByUserIdDescWithRedis] No Data touristAttractionsEntity");
                continue;
            }
            // 가져온 데이터가 있으면 redis 에 저장 및 반환
            TouristAttractionsResponseRedisDto redisDto = TouristAttractionsResponseRedisDto.createDto(touristAttractionsEntity);
            touristAttractionsRedisRepository.save(redisDto);

            result.add(redisDto);
        }
        return result;
    }
}