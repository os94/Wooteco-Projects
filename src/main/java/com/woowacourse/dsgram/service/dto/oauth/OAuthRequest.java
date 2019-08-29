package com.woowacourse.dsgram.service.dto.oauth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class OAuthRequest {
    private String code;
    private String client_id;
    private String client_secret;

    public OAuthRequest(String code, String client_id, String client_secret) {
        this.code = code;
        this.client_id = client_id;
        this.client_secret = client_secret;
    }
}
