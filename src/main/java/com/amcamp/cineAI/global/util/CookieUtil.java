package com.amcamp.cineAI.global.util;

import static com.amcamp.cineAI.global.common.constants.SecurityConstants.REFRESH_TOKEN_COOKIE_NAME;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class CookieUtil {

    public HttpHeaders generateRefreshTokenCookie(String refreshToken) {
        ResponseCookie refreshTokenCookie =
                ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                        .path("/")
                        .secure(false)
                        .sameSite(Cookie.SameSite.NONE.attributeValue()) // https only none
                        .httpOnly(true) // js에서 접근 금지
                        .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return headers;
    }

    String getRefreshTokenFromCookie() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();

        // 쿠키에서 리프레시 토큰을 가져오는 로직
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (jakarta.servlet.http.Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public HttpHeaders deleteRefreshTokenCookie() {
        ResponseCookie refreshTokenCookie =
                ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, "")
                        .path("/")
                        .maxAge(0)
                        .secure(false)
                        .sameSite(Cookie.SameSite.NONE.attributeValue())
                        .httpOnly(true)
                        .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return headers;
    }
}
