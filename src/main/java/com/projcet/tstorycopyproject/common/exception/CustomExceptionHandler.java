package com.projcet.tstorycopyproject.common.exception;

import com.projcet.tstorycopyproject.common.errorcode.CommonErrorCode;
import com.projcet.tstorycopyproject.common.CustomResponse;
import com.projcet.tstorycopyproject.common.errorcode.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex){
        log.error("Exception",ex);
        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> productException(CustomException t){
        ErrorCode errorCode = t.getErrorCode();
        log.error("{}",errorCode);
        return handleExceptionInternal(errorCode);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, CommonErrorCode.INVALID_PARAMETER);
    }
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode){
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private CustomResponse<Object> makeErrorResponse(ErrorCode errorCode){
        return CustomResponse.builder()
                .message(errorCode.getMessage())
                .codeNum(errorCode.getHttpStatus().value())
                .build();
    }
    // @Valid 어노테이션으로 넘어오는 에러 처리 메세지를 보내기 위한 메소드
    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(e, errorCode));
    }

    // 코드 가독성을 위해 에러 처리 메세지를 만드는 메소드 분리
    private CustomResponse<Object> makeErrorResponse(BindException e, ErrorCode errorCode) {
        List<CustomResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(CustomResponse.ValidationError::of)
                .collect(Collectors.toList());

        return CustomResponse.builder()
                .message(errorCode.getMessage())
                .valid(validationErrorList)
                .build();
    }
}
