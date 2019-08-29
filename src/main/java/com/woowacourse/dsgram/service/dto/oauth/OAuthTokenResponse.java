package com.woowacourse.dsgram.service.dto.oauth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class OAuthTokenResponse {
    private String access_token;
    private String scope;
}
