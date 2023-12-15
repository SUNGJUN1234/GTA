package com.jsj.GTA.domain.touristAttractions.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TouristAttractionsRedisRepository extends CrudRepository<TouristAttractionsResponseRedisDto, String> {
    Optional<TouristAttractionsResponseRedisDto> findByLatAndLng(String lat, String lng);
}
