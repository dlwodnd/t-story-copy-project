package com.projcet.tstorycopyproject.domain.user.errorcode;

import com.projcet.tstorycopyproject.common.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"유저를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다."),
    DUPLICATION_NICKNAME(HttpStatus.BAD_REQUEST,"닉네임 중복입니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
