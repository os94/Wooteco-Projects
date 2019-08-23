package com.woowacourse.dsgram.service.dto.oauth;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Because of object mapping, use snake case.
 */
@Getter
@NoArgsConstructor
public class OAuthTokenResponse {
    private String access_token;
    private String scope;
}
