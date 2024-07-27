package xyz.wavit.global.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstant {

    public static final String JWT_ROLE_CLAIM_NAME = "role";
    public static final String ACCESS_TOKEN_HEADER_PREFIX = "Bearer ";
}
