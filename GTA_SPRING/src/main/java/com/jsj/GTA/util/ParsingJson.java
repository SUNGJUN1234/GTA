package com.jsj.GTA.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsj.GTA.domain.touristAttractions.TourDestBaseInfo;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

@NoArgsConstructor
public class ParsingJson {

    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
    public static InputStream getNetworkConnection(HttpURLConnection urlConnection) throws IOException {
        urlConnection.setConnectTimeout(3000);
        urlConnection.setReadTimeout(3000);
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);

        if(urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
        }

        return urlConnection.getInputStream();
    }

    /* InputStream을 전달받아 문자열로 변환 후 반환 */
    public static String inputStreamToString(InputStream stream) throws IOException{
        StringBuilder result = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

        String readLine;
        while((readLine = br.readLine()) != null) {
            result.append(readLine + "\n\r");
        }

        br.close();

        return result.toString();
    }

    public static TourDestBaseInfo JsonToTourDestBaseInfo(String json) {
        TourDestBaseInfo items = null;
        try{
            ObjectMapper mapper = new ObjectMapper();
            items = mapper.readValue(json, TourDestBaseInfo.class);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}
