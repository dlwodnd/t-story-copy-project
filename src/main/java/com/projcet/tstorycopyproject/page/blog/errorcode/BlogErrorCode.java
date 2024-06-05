package com.projcet.tstorycopyproject.page.blog.errorcode;

import com.projcet.tstorycopyproject.global.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BlogErrorCode implements ErrorCode {
    NOT_MATCH_USER(HttpStatus.BAD_REQUEST,"유저가 일치하지 않습니다."),
    NOT_FOUND_BLOG(HttpStatus.NOT_FOUND,"블로그를 찾을 수 없습니다."),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND,"카테고리를 찾을 수 없습니다."),
    NOT_FOUND_FEED(HttpStatus.NOT_FOUND,"피드를 찾을 수 없습니다."),
    NOT_FOUND_FEED_CMT(HttpStatus.NOT_FOUND,"피드 댓글을 찾을 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;

}
