package com.projcet.tstorycopyproject.domain.mail.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailCertificationRq {
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@([a-zA-Z0-9]{3,}\\.[a-z]{2,3}|[a-zA-Z0-9]{3,}\\.[a-z]{2,3}\\.[a-z]{2,3})$",message = "올바른 이메일 형식이 아닙니다.")
    @NotEmpty(message = "이메일을 입력해 주세요")
    @Schema(description= "이메일",defaultValue = "email")
    private String email;
}
