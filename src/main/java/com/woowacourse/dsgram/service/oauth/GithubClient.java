package com.woowacourse.dsgram.service.oauth;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "oauth.client.github")
public class GithubClient {
    private static final Logger log = LoggerFactory.getLogger(GithubClient.class);

    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String CODE = "code";

    private String clientId;
    private String clientSecret;

    private String tokenUrl;
    private String userInfoUrl;
    private String userEmailUrl;

    public String getToken(String code) {
        UrlConnector<String> template = connection -> {
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            setUpBody(connection, code);

            return getJsonElement(connection)
                    .getAsJsonObject().get("access_token").getAsString();
        };
        return template.connectHttpUrl(tokenUrl);
    }

    private void setUpBody(HttpURLConnection connection, String code) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLIENT_ID, clientId);
        jsonObject.addProperty(CLIENT_SECRET, clientSecret);
        jsonObject.addProperty(CODE, code);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(jsonObject.toString().getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonElement getJsonElement(HttpURLConnection connection) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder result = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            return new JsonParser().parse(result.toString());
        } catch (IOException e) {
            throw new RuntimeException("뭘 적어야하지...?", e);
        }
    }

    public String getUserEmail(String accessToken) {
        UrlConnector<String> template = connection -> {
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Authorization", String.format("token %s", accessToken));

            return getJsonElement(connection).getAsJsonArray().get(0)
                    .getAsJsonObject().get("email").getAsString();
        };
        return template.connectHttpUrl(userEmailUrl);
    }

    public JsonElement getUserInformation(String accessToken) {
        UrlConnector<JsonElement> template = connection -> {
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Authorization", String.format("token %s", accessToken));
            return getJsonElement(connection);
        };
        return template.connectHttpUrl(userInfoUrl);
    }
}

