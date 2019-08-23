package com.woowacourse.dsgram.service.dto.oauth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
public class OAuthEmailResponse {
    private String email;
    private boolean primary;
    private boolean verified;
    private String visibility;
}
