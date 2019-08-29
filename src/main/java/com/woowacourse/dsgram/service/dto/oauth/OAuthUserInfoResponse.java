package com.woowacourse.dsgram.service.dto.oauth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class OAuthUserInfoResponse {
    private String login;
    private long id;
    private String avatar_url;
    private String html_url;
    private String name;
    private String email;

    @Builder
    public OAuthUserInfoResponse(String login, long id, String avatar_url, String html_url, String name, String email) {
        this.login = login;
        this.id = id;
        this.avatar_url = avatar_url;
        this.html_url = html_url;
        this.name = name;
        this.email = email;
    }
}
