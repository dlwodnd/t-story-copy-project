package com.projcet.tstorycopyproject.page.posts.errorcode;

import com.projcet.tstorycopyproject.global.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostsErrorCode implements ErrorCode {
    NOT_FOUND_CAT(HttpStatus.NOT_FOUND, "해당 카테고리를 찾을 수 없습니다."),
    NOT_FOUND_FEED(HttpStatus.NOT_FOUND, "해당 피드를 찾을 수 없습니다."),
    NOT_FOUND_FEED_PIC(HttpStatus.NOT_FOUND, "해당 피드 사진을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
