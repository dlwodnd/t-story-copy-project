package com.projcet.tstorycopyproject.domain.user.request;

import com.projcet.tstorycopyproject.domain.mail.request.EmailCertificationRq;
import com.projcet.tstorycopyproject.domain.mail.response.EmailCertificationRp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRq {
    private EmailCertificationRp emailCertificationRp;
    private String password;
    private String name;
    private String nickname;
}
