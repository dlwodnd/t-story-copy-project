package com.projcet.tstorycopyproject.domain.mail.errorcode;

import com.projcet.tstorycopyproject.global.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum EmailErrorCode implements ErrorCode {
    ALREADY_USED_EMAIL(HttpStatus.BAD_REQUEST,"이미 사용중인 이메일입니다."),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND,"이메일을 찾을 수 없습니다."),
    INVALID_CERTIFICATION_NUMBER(HttpStatus.BAD_REQUEST,"인증번호가 일치하지 않습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
