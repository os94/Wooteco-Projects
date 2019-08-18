package com.woowacourse.dsgram.service.oauth;

import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@FunctionalInterface
public interface UrlConnector<T> {
    T setUpConnection(HttpURLConnection connection) throws IOException;

    default T connectHttpUrl(String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.addRequestProperty("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            connection.addRequestProperty("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);

            return setUpConnection(connection);
        } catch (IOException e) {
            throw new RuntimeException("요청 연결이 실패하였습니다.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
