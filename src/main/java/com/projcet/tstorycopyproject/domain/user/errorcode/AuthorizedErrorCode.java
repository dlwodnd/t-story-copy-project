package com.projcet.tstorycopyproject.domain.user.errorcode;

import com.projcet.tstorycopyproject.global.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthorizedErrorCode implements ErrorCode {
    REFRESH_TOKEN_IS_EXPIRATION(HttpStatus.NOT_FOUND,"리프레쉬 토큰 기간이 만료되었습니다.")
    ,NOT_EXISTS_REFRESH_TOKEN(HttpStatus.BAD_REQUEST,"리프레쉬 토큰을 찾을 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
