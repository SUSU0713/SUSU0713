package com.mashibing.response;

import lombok.Data;

@Data
public class TokenResponse {
    private String refreshToken;

    private String accessToken;

    public TokenResponse(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
