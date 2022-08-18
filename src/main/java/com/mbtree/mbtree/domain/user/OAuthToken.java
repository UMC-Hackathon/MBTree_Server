package com.mbtree.mbtree.domain.user;

import lombok.Builder;
import lombok.Data;
import org.apache.catalina.User;

@Data
public class OAuthToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private Long expires_in;
    private String scope;
    private Long refresh_token_expires_in;

}
