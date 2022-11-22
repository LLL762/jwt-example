package com.delacasa.jwt.jwt;

import org.springframework.security.core.Authentication;

public interface JwtUtil {
    String createToken(Authentication authResult);

}
