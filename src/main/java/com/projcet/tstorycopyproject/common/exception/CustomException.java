package com.projcet.tstorycopyproject.common.exception;

import com.projcet.tstorycopyproject.common.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
