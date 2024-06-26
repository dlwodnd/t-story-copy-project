package com.projcet.tstorycopyproject.global.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)//타겟은 파라미터 레벨
@Retention(RetentionPolicy.RUNTIME)//런타임까지 어노테이션 정보가 남아있음
@Documented
@Schema(hidden = true)
public @interface Login {
    boolean required() default true;
}
