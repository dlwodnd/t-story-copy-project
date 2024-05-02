package com.projcet.tstorycopyproject.domain.feed.errorcode;

import com.projcet.tstorycopyproject.common.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum FeedErrorCode implements ErrorCode {
    MISS_MATCH_PW(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다."),
    NOT_FOUND_FEED_CMT(HttpStatus.NOT_FOUND,"피드 댓글을 찾을 수 없습니다."),
    GUEST_USER_NEED_ID_AND_PW(HttpStatus.UNAUTHORIZED,"게스트 유저는 아이디와 비밀번호를 입력해주세요"),
    NEED_LOGIN(HttpStatus.UNAUTHORIZED,"로그인이 필요합니다"),
    NOT_FOUND_CAT(HttpStatus.NOT_FOUND,"카테고리를 찾을 수 없습니다."),
    NOT_FOUND_FEED_PIC(HttpStatus.NOT_FOUND,"피드 사진을 찾을 수 없습니다."),
    NOT_FOUND_FEED(HttpStatus.NOT_FOUND,"피드를 찾을 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
