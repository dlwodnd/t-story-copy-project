package com.projcet.tstorycopyproject.page.user.errorcode;

import com.projcet.tstorycopyproject.global.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_EXISTS_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "존재하지 않는 리프레시 토큰입니다."),
    REFRESH_TOKEN_IS_EXPIRATION(HttpStatus.BAD_REQUEST, "리프레시 토큰이 만료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
