package com.projcet.tstorycopyproject.page.user.service;

import com.projcet.tstorycopyproject.global.auth.jwt.JwtProperties;
import com.projcet.tstorycopyproject.global.auth.jwt.JwtTokenProvider;
import com.projcet.tstorycopyproject.global.exception.CustomException;
import com.projcet.tstorycopyproject.global.repository.UserRepository;
import com.projcet.tstorycopyproject.global.security.MyPrincipal;
import com.projcet.tstorycopyproject.global.security.MyUserDetails;
import com.projcet.tstorycopyproject.global.utils.CookieUtils;
import com.projcet.tstorycopyproject.page.user.errorcode.UserErrorCode;
import com.projcet.tstorycopyproject.page.user.response.UserAccessTokenRp;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final CookieUtils cookieUtils;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties JWTProperties;
    // 유저 정보

    // 유저 로그아웃
    public Void userLogout(HttpServletResponse response) {
        cookieUtils.deleteCookie(response,"rt");
        return null;
    }

    // 리프레쉬 토큰 재발급
    public UserAccessTokenRp refreshAccessToken(HttpServletRequest request ) {

        Cookie userCookie = cookieUtils.getCookie(request, "rt").get();
        if (userCookie == null) {
            throw new CustomException(UserErrorCode.NOT_EXISTS_REFRESH_TOKEN);
        }
        String token = userCookie.getValue();
        if (!jwtTokenProvider.isValidateToken(token)) {
            throw new CustomException(UserErrorCode.REFRESH_TOKEN_IS_EXPIRATION);
        }
        MyUserDetails myUserDetails = (MyUserDetails) jwtTokenProvider.getUserDetailsFromToken(token);
        MyPrincipal myprincipal = myUserDetails.getMyPrincipal();
        String at = jwtTokenProvider.generateAccessToken(myprincipal);

        return new UserAccessTokenRp(at);
    }
}
