package com.projcet.tstorycopyproject.domain.mail.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailCertificationRp {
    private String email;
    private long result;
}
