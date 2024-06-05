package com.projcet.tstorycopyproject.page.manage.errorcode;

import com.projcet.tstorycopyproject.global.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ManageErrorCode implements ErrorCode {
    NOT_FOUND_TOP_CAT(HttpStatus.NOT_FOUND, "존재하지 않는 상위 카테고리입니다."),
    ALREADY_SUB_CAT(HttpStatus.BAD_REQUEST, "이미 하위 카테고리가 존재하는 카테고리입니다."),
    NOT_FOUND_CAT(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
    NOT_MATCH_BLOG(HttpStatus.BAD_REQUEST, "해당 블로그에 속한 카테고리가 아닙니다."),
    NOT_FOUND_BLOG(HttpStatus.NOT_FOUND, "존재하지 않는 블로그입니다."),
    DUPLICATE_CAT_ORDER(HttpStatus.BAD_REQUEST, "중복된 카테고리 순서입니다."),;
    private final HttpStatus httpStatus;
    private final String message;

}
