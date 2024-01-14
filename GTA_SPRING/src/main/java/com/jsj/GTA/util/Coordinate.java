package com.jsj.GTA.util;

import com.jsj.GTA.domain.touristAttractions.TouristAttractionsListResponseDto;
import com.jsj.GTA.domain.touristAttractions.redis.TouristAttractionsResponseRedisDto;
import com.jsj.GTA.service.OAuth2UserService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class Coordinate {

    private final Logger LOGGER = LoggerFactory.getLogger(Coordinate.class);

    public static List<TouristAttractionsListResponseDto> findClosestCoordinates(List<TouristAttractionsListResponseDto> coordinates, int count, double lat, double lng) {
        if (coordinates.size() == 0 || count <= 0) {
            // 좌표 리스트가 비어 있거나 count가 0 이하일 경우 빈 리스트 반환
            return List.of();
        }

        return coordinates.stream()
                .sorted(Comparator.comparingDouble(coordinate -> {
                    double listLat = Double.parseDouble(coordinate.getLat());
                    double listLng = Double.parseDouble(coordinate.getLng());
                    return Math.sqrt(Math.pow(listLat - lat, 2) + Math.pow(listLng - lng, 2));
                }))
                .limit(count)
                .collect(Collectors.toList());
    }
    public static List<TouristAttractionsResponseRedisDto> findClosestCoordinatesWithRedis(List<TouristAttractionsResponseRedisDto> coordinates, int count, double lat, double lng) {
        if (coordinates.size() == 0 || count <= 0) {
            // 좌표 리스트가 비어 있거나 count가 0 이하일 경우 빈 리스트 반환
            return List.of();
        }

        return coordinates.stream()
                .sorted(Comparator.comparingDouble(coordinate -> {
                    double listLat = coordinate.getLat();
                    double listLng = coordinate.getLng();
                    return Math.sqrt(Math.pow(listLat - lat, 2) + Math.pow(listLng - lng, 2));
                }))
                .limit(count)
                .collect(Collectors.toList());
    }
}