package com.projcet.tstorycopyproject.global.security;

import com.projcet.tstorycopyproject.global.entity.jpa_enum.SocialEnum;
import com.projcet.tstorycopyproject.global.entity.jpa_enum.UserRoleEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {
    private Long userPk;
    private String userEmail;
    private String userPw;
    private String userPic;
    private String nickname;
    private SocialEnum socialType;
    private UserRoleEnum role;
}
