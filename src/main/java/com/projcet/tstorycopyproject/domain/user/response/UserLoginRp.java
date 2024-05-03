package com.projcet.tstorycopyproject.domain.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRp {
    private Long userPk;
    private String email;
    private String nickname;
    private String accessToken;
}
