package com.jsj.GTA.util;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {

    private static final String BASE_URL = "https://m.map.naver.com/search2/search.naver?query=";
    private static final Pattern ID_PATTERN = Pattern.compile("\"id\": \"(\\d+)\"");
    /**
     * 시설 관리자가 올려놓은 사진
     */
    private static final Pattern PHOTO_PATTERN = Pattern.compile("\"__typename\":\"Image\",\"origin\":\"(.*?)\"");
    /**
     * 시설 방문자가 올려놓은 사진
     */
    private static final Pattern VISITOR_PHOTO_PATTERN = Pattern.compile("\"thumbnailUrlList\":\\[([^\\]]*)\\]");

    /**
     * url의 "\\u002F" 를 "/" 로 변환
     * @param url
     * @return
     */
    public static String decodeUrlsUnicodeToSlash(String url) {
        return url.replace("\\u002F", "/");
    }

    /**
     * 특정 장소 사진 url 추출하기
     *
     * @param html    HTML 문서
     * @return 사진 url 리스트
     */
    public static List<String> getMatcherPlacePhoto(String html) {
        Pattern pattern = PHOTO_PATTERN;
        Pattern visitorPattern = VISITOR_PHOTO_PATTERN;
        Matcher matcher = pattern.matcher(html);
        Matcher visitorMatcher = visitorPattern.matcher(html);
        ArrayList<String> list = new ArrayList<>();
        // 시설 관리자가 올린 사진 우선
        while (matcher.find()) {
            String photoUrl = decodeUrlsUnicodeToSlash(matcher.group(1));
            list.add(photoUrl);
        }
        // 시설 관리자가 올린 사진이 없으면 방문자 사진으로
        if(list.isEmpty()&&visitorMatcher.find()) {
                String thumbnailUrlListStr = visitorMatcher.group(1);
                // 각 URL 추출
                Pattern urlPattern = Pattern.compile("\"([^\"\\\\]*(?:\\\\.[^\"\\\\]*)*)\"");
                Matcher urlMatcher = urlPattern.matcher(thumbnailUrlListStr);
            while (urlMatcher.find()) {
                String photoUrl = decodeUrlsUnicodeToSlash(urlMatcher.group(1));
                list.add(photoUrl);
            }
        }
        return list;
    }

    /**
     * html 에서 특정 장소의 id 값 추출하기
     *
     * @param html    HTML 문서
     * @return 장소 id
     */
    public static String getMatcherPlaceId(String html) {
        Pattern pattern = ID_PATTERN;
        Matcher matcher = pattern.matcher(html);
        return matcher.find() ? matcher.group(1) : null;
    }

    /**
     * 입력된 장소의 검색결과 html 추출하기
     *
     * @param url 요청할 장소 주소
     * @return url의 html
     * @throws IOException
     */
    public static String getHTMLDocument(String url) throws IOException {
        return Jsoup.connect(url).get().toString();
    }

    /**
     * 입력된 장소명에 대한 대표 사진 리스트 반환
     *
     * @param placeName 검색할 장소명
     * @return 대표 사진 리스트
     * @throws IOException
     */
    public static List<String> getImages(String placeName) throws IOException {
        // 1. 네이버 지도에 특정 장소 검색
        String mapHtml = getHTMLDocument(BASE_URL+placeName);
        // 2. 장소의 특정 id 추출
        String id = getMatcherPlaceId(mapHtml);
        // 3. id를 기반으로 네이버 플레이스 사진 페이지 접근
        String mapPhotoUrl = "https://m.place.naver.com/place/" + id + "/photo";
        String mapPhotoHtml = getHTMLDocument(mapPhotoUrl);
        // 4. 특정 장소의 대표 사진 리스트 불러오기
        return getMatcherPlacePhoto(mapPhotoHtml);
    }
}
