package com.projcet.tstorycopyproject.domain.catagory.errorcode;

import com.projcet.tstorycopyproject.global.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CatErrorCode implements ErrorCode {
    NOT_MATCH_BLOG(HttpStatus.BAD_REQUEST,"블로그에 속하지 않는 카테고리입니다."),
    NOT_FOUND_CAT(HttpStatus.NOT_FOUND,"카테고리를 찾을 수 없습니다."),
    DUPLICATE_CAT_ORDER(HttpStatus.BAD_REQUEST,"카테고리 순서가 중복됩니다."),
    ALREADY_SUB_CAT(HttpStatus.BAD_REQUEST,"하위 카테고리에게는 종속 할 수 없습니다."),
    NOT_FOUND_TOP_CAT(HttpStatus.NOT_FOUND,"상위 카테고리를 찾을 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
