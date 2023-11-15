package com.jsj.GTA.api.touristAttractions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jsj.GTA.util.ParsingJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * TouristAttractions 와 외부 API 연결
 */
@RestController
@JsonIgnoreProperties(ignoreUnknown = true)
public class TouristAttractionsRepository {

    private static String baseUrl;
    private static String serviceKey;

    public TouristAttractionsRepository(
            @Value("${api.servicekey}")String service_key,
            @Value("${api.baseurl}")String base_url
    ) {
        baseUrl = base_url;
        serviceKey = service_key;
    }

    /**
     * 공공 API 데이터 호출 (단일 조건)
     * @param filterKey
     * @param filterValue
     * @return TourDestBaseInfo 1차 가공된 공공 API 데이터
     * @throws JsonProcessingException
     */
    public static TourDestBaseInfo callApi(String filterKey, String filterValue) {
        String apiUrl = baseUrl +
                "serviceKey=" + serviceKey +
                "&type=json";
        HttpURLConnection urlConnection = null;
        InputStream stream;
        String result = null;

        if (filterKey != null) {
            if (filterKey.equals("id")) filterKey = "pageNo";
            apiUrl += "&numOfRows=1" + "&" + filterKey + "=" + filterValue;
        }
        System.out.println("apiUrl = " + apiUrl);
        try {
            URL url = new URL(apiUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            stream = ParsingJson.getNetworkConnection(urlConnection);
            result = ParsingJson.inputStreamToString(stream);

            if (stream != null) stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        TourDestBaseInfo item = ParsingJson.JsonToTourDestBaseInfo(result);
        return item;
    }

    /**
     * 공공 API 데이터 호출 (복수 조건)
     * @param conditions
     * @return TourDestBaseInfo 1차 가공된 공공 API 데이터
     * @throws JsonProcessingException
     */
    public static TourDestBaseInfo callApi2(String conditions) {
        String apiUrl = baseUrl +
                "serviceKey=" + serviceKey +
                "&type=json";
        HttpURLConnection urlConnection = null;
        InputStream stream;
        String result = null;
        apiUrl += "&numOfRows=1" + "&" + conditions;
        System.out.println("apiUrl = " + apiUrl);
        try {
            URL url = new URL(apiUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            stream = ParsingJson.getNetworkConnection(urlConnection);
            result = ParsingJson.inputStreamToString(stream);

            if (stream != null) stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        TourDestBaseInfo item = ParsingJson.JsonToTourDestBaseInfo(result);
        return item;
    }

    /**
     * 관광지 id로 조회
     *
     * @param id
     * @return TouristAttractions
     */
    public TouristAttractions findById(String id) {
        TourDestBaseInfo item = callApi("id", id);
        return item.getTourDestBaseInfo().get(0);
    }

    /**
     * 전체 관광지 조회
     *
     * @return List<TouristAttractions>
     */
    public List<TouristAttractions> findAllDesc() {
        TourDestBaseInfo item = callApi(null, null);
        return item.getTourDestBaseInfo();
    }

    /**
     * 스탬프에 해당하는 하나의 관광지 조회
     * @return TouristAttractions
     */
    public TouristAttractions findByStampsId(String id) {
        TourDestBaseInfo item = callApi("id", id);
        return item.getTourDestBaseInfo().get(0);
    }

    /**
     * 주어진 좌표를 가진 관광지 조회
     * @return TouristAttractions
     */
    public TouristAttractions findByCoordinate(double lat, double lng) {
        String conditions;
        conditions = "lat="+lat+"&"+"lng="+lng;
        TourDestBaseInfo item = callApi2(conditions);
        return item.getTourDestBaseInfo().get(0);
    }
}

